package com.example.studentportal.service;

import com.example.studentportal.dto.request.RoleRequest;
import com.example.studentportal.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Role> findAll();

    public Optional<Role> findById(Integer id);

    public Role save(RoleRequest request);

    public void deleteById(Integer id);
}
