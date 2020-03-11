package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.model.Comment;
import com.example.accessingdatamysql.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByUser_Id(Integer userId, Pageable pageable);
    Page<Comment> findAllByDocument_Id(Integer userId, Pageable pageable);
}
