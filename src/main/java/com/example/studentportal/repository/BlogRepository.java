package com.example.studentportal.repository;

import com.example.studentportal.model.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Integer> {
    List<Blog> findAllByUserId(int userId);

    Blog findById(int blogId);
}
