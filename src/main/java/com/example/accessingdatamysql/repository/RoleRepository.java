package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findAllByName(String role);
    Role findAllById(Integer id);
}
