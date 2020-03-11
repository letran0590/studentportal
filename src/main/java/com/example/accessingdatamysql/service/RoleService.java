package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.CreateUserRequest;
import com.example.accessingdatamysql.dto.request.RoleRequest;
import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Role> findAll();

    public Optional<Role> findById(Integer id);

    public Role save(RoleRequest request);

    public void deleteById(Integer id);
}
