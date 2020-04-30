package com.example.studentportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<User> findAllByTutorId(int tutorId, Pageable pageable);
    Page<User> findAllByTutorFlagAndRole_Id(boolean tutorFlag, Integer roleId, Pageable pageable);
    Page<User> findAllByRoleId(int roleId, Pageable pageable);
    void deleteById(int userId);
//    User findById(int userId);
}
