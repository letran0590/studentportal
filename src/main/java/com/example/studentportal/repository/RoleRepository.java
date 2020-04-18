package com.example.studentportal.repository;

import com.example.studentportal.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findAllByName(String role);
    Role findAllById(Integer id);
}
