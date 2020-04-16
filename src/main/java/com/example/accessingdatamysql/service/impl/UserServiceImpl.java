package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dto.request.*;
import com.example.accessingdatamysql.dto.request.filter.UserFilterRequest;
import com.example.accessingdatamysql.dto.response.UserResponseDto;
import com.example.accessingdatamysql.exception.ResourceNotFoundException;
import com.example.accessingdatamysql.exception.UserExistException;
import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
import com.example.accessingdatamysql.repository.RoleRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserExistException(String.format("User with email %s existed!", request.getEmail()));
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(request, User.class);
        Role role = roleRepository.findAllByName(request.getRole());
        if (role == null) {
            throw new ResourceNotFoundException("Role name " + request.getRole() + " not found");
        } else {
            user.setRole(role);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> assignStudents(UpdateUserRequest request) {
        User tutor = userRepository.findById(request.getTutorId()).orElseThrow(() -> new ResourceNotFoundException("tutorId " + request.getTutorId() + " not found"));
        if(!CollectionUtils.isEmpty(request.getStudentIds())) {
            List<User> userList = userRepository.findAllByIdIn(request.getStudentIds());
            userList.stream().forEach(user -> {
                user.setTutor(tutor);
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
        if(user == null){
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
    public UserResponseDto updateInfo(UpdateInfoRequest request){
        User user = userRepository.findByEmail(request.getEmail());

        if(user == null){
            throw new ResourceNotFoundException("Email is invalid");
        }else{
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

        if(user == null){
            throw new ResourceNotFoundException("Email is invalid");
        }else{
            if(!user.getPassword().equals(request.getPassword())){
                throw new ResourceNotFoundException("Password is not correct");
            }else{
                user.setPassword(request.getNewPassword());

                userRepository.save(user);

                UserResponseDto response = UserResponseDto.fromUser(user);
                return response;
            }
        }
    }
}
