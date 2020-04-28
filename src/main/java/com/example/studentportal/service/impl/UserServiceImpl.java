package com.example.studentportal.service.impl;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.config.FileStorageProperties;
import com.example.studentportal.dto.request.*;
import com.example.studentportal.dto.request.filter.UserFilterRequest;
import com.example.studentportal.dto.response.UserResponseDto;
import com.example.studentportal.exception.FileStorageException;
import com.example.studentportal.exception.ResourceNotFoundException;
import com.example.studentportal.exception.UserExistException;
import com.example.studentportal.model.Role;
import com.example.studentportal.model.User;
import com.example.studentportal.repository.RoleRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Path fileStorageLocation;
    private final FileStorageProperties fileStorageProperties;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final RoleRepository roleRepository,
                           final FileStorageProperties fileStorageProperties, FileStorageProperties fileStorageProperties1) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadAvatarDir())
                .toAbsolutePath().normalize();
        this.fileStorageProperties = fileStorageProperties1;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getListUserByFilter(UserFilterRequest filterRequest) {
        return userRepository.findAllByTutorFlagAndRole_Id(filterRequest.isTutorFlag(), filterRequest.getRoleId());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(CreateUserRequest request) {
        // Check exist
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistException(String.format("User with email %s existed!", request.getEmail()));
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(request, User.class);
        setRole(request, user);
        user.setAvatarViewUrl("http://localhost:9090/avatar/noimage_person.png");
        return userRepository.save(user);
    }

    private void setRole(CreateUserRequest request, User user) {
        Role role = roleRepository.findAllByName(request.getRole());
        if (role == null) {
            throw new ResourceNotFoundException("Role name " + request.getRole() + " not found");
        } else {
            user.setRole(role);
        }
    }

    @Override
    public List<User> assignStudents(UpdateUserRequest request) {
        User tutor = userRepository.findById(request.getTutorId()).orElseThrow(() -> new ResourceNotFoundException("tutorId " + request.getTutorId() + " not found"));
        if (!CollectionUtils.isEmpty(request.getStudentIds())) {
            List<User> userList = userRepository.findAllByIdIn(request.getStudentIds());
            userList.stream().forEach(user -> {
                user.setTutor(tutor);
                user.setTutorFlag(true);
            });
            userRepository.saveAll(userList);
            return userList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public UserResponseDto login(LoginRequest request) {
        User user = userRepository.findAllByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        } else {
            UserResponseDto response = UserResponseDto.fromUser(user);
            return response;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto updateInfo(UpdateInfoRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new ResourceNotFoundException("Email is invalid");
        } else {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setDob(request.getDob());
            user.setAddress(request.getAddress());

            userRepository.save(user);

            UserResponseDto response = UserResponseDto.fromUser(user);
            return response;
        }
    }

    @Override
    public UserResponseDto updatePassword(UpdatePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new ResourceNotFoundException("Email is invalid");
        } else {
            if (!user.getPassword().equals(request.getPassword())) {
                throw new ResourceNotFoundException("Password is not correct");
            } else {
                user.setPassword(request.getNewPassword());

                userRepository.save(user);

                UserResponseDto response = UserResponseDto.fromUser(user);
                return response;
            }
        }
    }

    @Override
    public List<User> getStudentsByTutorId(int tutorId) {
        return userRepository.findAllByTutorId(tutorId);
    }

    @Override
    public User uploadAvatar(int userId, MultipartFile fileName) throws IOException {
        User user = getUser(userId);
        // Normalize file name
        String file = StringUtils.cleanPath(fileName.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(file.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file);
            }
            // create avatar file
            createDirectory(userId);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(file);
            Files.copy(fileName.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(ApiConstant.USER + ApiConstant.DOWNLOAD + "/")
                    .path(file)
                    .toUriString();
            String viewUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(ApiConstant.AVATAR + "/")
                    .path(file)
                    .toUriString();
            user.setAvatarDownloadUri(fileDownloadUri);
            user.setAvatarFilePath(targetLocation.toString());
            user.setAvatarViewUrl(viewUrl);
            userRepository.save(user);
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        return user;
    }

    @Override
    public void importUsers(MultipartFile reapExcelDataFile) throws IOException {
        List<User> tempStudentList = new ArrayList<User>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Role studentRole = roleRepository.findAllByName("Student");
        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            User tempStudent = new User();

            XSSFRow row = worksheet.getRow(i);

            tempStudent.setFirstName(row.getCell(0).getStringCellValue());
            tempStudent.setLastName(row.getCell(1).getStringCellValue());
            tempStudent.setGender(row.getCell(2).getStringCellValue());
            tempStudent.setEmail(row.getCell(3).getStringCellValue());
            tempStudent.setAddress(row.getCell(4).getStringCellValue());
            tempStudent.setDob(row.getCell(5).getDateCellValue());
            tempStudent.setPassword(fileStorageProperties.getDefaultPassword());
            if(null != studentRole){
                tempStudent.setRole(studentRole);
            }
            tempStudentList.add(tempStudent);
        }
        userRepository.saveAll(tempStudentList);
    }

    private void createDirectory(int userId) {
        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadAvatarDir() + "/" + userId)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private User getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return userOptional.get();
    }
}
