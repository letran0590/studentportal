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

    void deleteById(Integer id);

    UserResponseDto login(LoginRequest request);

    User findUserByEmail(String email);

    UserResponseDto updateInfo(UpdateInfoRequest request);

    UserResponseDto updatePassword(UpdatePasswordRequest request);

    User uploadAvatar(int userId, MultipartFile file) throws IOException;
}