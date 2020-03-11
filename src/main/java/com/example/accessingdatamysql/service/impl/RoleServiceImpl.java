package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dto.request.RoleRequest;
import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.repository.RoleRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.RoleService;
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
