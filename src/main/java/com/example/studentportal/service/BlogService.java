package com.example.studentportal.service;

import com.example.studentportal.dto.request.CreateBlogRequest;
import com.example.studentportal.model.Blog;

import java.util.List;

public interface BlogService {

    Blog save(CreateBlogRequest request);

    Blog update(String body, int userId, int blogId) throws Exception;

    List<Blog> getAllBlogsByUserId(int userId);

}
