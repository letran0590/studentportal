package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.model.Chat;
import com.example.accessingdatamysql.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {
    Page<Chat> findAllByStudent_Id(Integer studentId, Pageable pageable);
    Page<Chat> findAllByTutor_Id(Integer tutorId, Pageable pageable);
    Page<Chat> findAll(Pageable pageable);
}
