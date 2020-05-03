package com.example.studentportal.repository;

import com.example.studentportal.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {
    Page<Chat> findAllByStudent_Id(Integer studentId, Pageable pageable);
    Page<Chat> findAllByTutor_Id(Integer tutorId, Pageable pageable);
    Page<Chat> findAllByStudent_IdAndTutor_Id(int studentId, int tutorId, Pageable pageable);
//    Page<Chat> findAllByStudent_IdAndTutor_IdAndDateChatFrom(int studentId, int tutorId, Date dateChat, Pageable pageable);
    Page<Chat> findAll(Pageable pageable);
}
