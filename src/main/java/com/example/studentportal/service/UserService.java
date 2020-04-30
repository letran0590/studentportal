package com.example.studentportal.service;

import com.example.studentportal.dto.request.*;
import com.example.studentportal.dto.request.filter.UserFilterRequest;
import com.example.studentportal.dto.response.UserResponseDto;
import com.example.studentportal.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    List<User> getListUserByFilter(UserFilterRequest filterRequest);

    Optional<User> findById(Integer id);

    User save(CreateUserRequest request);

    List<User> assignStudents(UpdateUserRequest request);

    void deleteById(int id, int adminId) throws Exception;

    UserResponseDto login(LoginRequest request);

    User findUserByEmail(String email);

    UserResponseDto updateInfo(UpdateInfoRequest request);

    UserResponseDto updatePassword(UpdatePasswordRequest request);

    List<User> getStudentsByTutorId(int tutorId, int start, int limit);

    public List<User> getAllStudents(int start, int limit);

    User uploadAvatar(int userId, MultipartFile file) throws IOException;

    void importUsers(MultipartFile reapExcelDataFile) throws IOException;

    void forgotPassword(String email) throws Exception;

    UserResponseDto changeRole(int userId, int roleId, int adminId) throws Exception;

    void adminChangePassword(int adminId, int userId, String password) throws Exception;
}
