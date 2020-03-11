package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.CreateUserRequest;
import com.example.accessingdatamysql.dto.request.LoginRequest;
import com.example.accessingdatamysql.dto.request.UpdateUserRequest;
import com.example.accessingdatamysql.dto.response.UserResponseDto;
import com.example.accessingdatamysql.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Integer id);

    User save(CreateUserRequest request);

    List<User> assignStudents(UpdateUserRequest request);

    void deleteById(Integer id);

    UserResponseDto login(LoginRequest request);

}
