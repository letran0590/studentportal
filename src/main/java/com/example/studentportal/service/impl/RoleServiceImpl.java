package com.example.studentportal.service.impl;

import com.example.studentportal.dto.request.RoleRequest;
import com.example.studentportal.model.Role;
import com.example.studentportal.repository.RoleRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Role save(RoleRequest request) {
        ModelMapper modelMapper = new ModelMapper();

        Role role = modelMapper.map(request,Role.class);
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {

    }
}
