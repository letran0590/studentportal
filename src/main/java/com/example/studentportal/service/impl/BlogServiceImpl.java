package com.example.studentportal.service.impl;

import com.example.studentportal.dto.request.CreateBlogRequest;
import com.example.studentportal.exception.ResourceNotFoundException;
import com.example.studentportal.model.Blog;
import com.example.studentportal.model.User;
import com.example.studentportal.repository.BlogRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Blog save(CreateBlogRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User is not found");
        }else{
            ModelMapper modelMapper = new ModelMapper();
            Blog blog = modelMapper.map(request, Blog.class);
            return blogRepository.save(blog);
        }
    }

    @Override
    public Blog update(String body, int userId, int blogId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new Exception("User don't have athorization to update");
        }else{
            Blog blog = blogRepository.findById(blogId);
            if (blog == null){
                throw new ResourceNotFoundException("BlogID is not valid");
            }else{
                blog.setBody(body);
                return blogRepository.save(blog);
            }
        }
    }

    @Override
    public List<Blog> getAllBlogsByUserId(int userId) {
        return blogRepository.findAllByUserId(userId);
    }
}
