package com.example.studentportal.repository;

import com.example.studentportal.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface DocumentRepository extends CrudRepository<Document, Integer> {
    Page<Document> findAllByStudent_Id(Integer studentId, Pageable pageable);
    Page<Document> findAllByTutor_Id(Integer tutorId, Pageable pageable);
    Page<Document> findAllByStudent_IdAndTutorId(Integer studentId, Integer tutorId, Pageable pageable);
    Page<Document> findAll(Pageable pageable);
}
