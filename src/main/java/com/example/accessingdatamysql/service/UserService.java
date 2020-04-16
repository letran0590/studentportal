package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.*;
import com.example.accessingdatamysql.dto.request.filter.UserFilterRequest;
import com.example.accessingdatamysql.dto.response.UserResponseDto;
import com.example.accessingdatamysql.model.User;

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
}
