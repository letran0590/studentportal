package com.example.studentportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.studentportal.model.User;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User findAllByEmailAndPassword(String email, String password);
    User findAllByIdAndRole_Id(Integer id, Integer roleId);
    List<User> findAllByIdIn(List<Integer> studentIds);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findAllByTutorId(int tutorId);
    List<User> findAllByTutorFlagAndRole_Id(boolean tutorFlag, Integer roleId);
}
