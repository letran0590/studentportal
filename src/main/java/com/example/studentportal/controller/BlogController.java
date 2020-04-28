package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.CreateBlogRequest;
import com.example.studentportal.dto.request.CreateUserRequest;
import com.example.studentportal.model.Blog;
import com.example.studentportal.model.User;
import com.example.studentportal.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@CrossOrigin
@RequestMapping(ApiConstant.BLOG) // This means URL's start with /demo (after Application path)
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/")
    public ResponseEntity<Blog> create(@RequestBody @Valid CreateBlogRequest request){
        return ResponseEntity.ok(blogService.save(request));
    }

    @GetMapping("/filter")
    public List<Blog> findAllBLogByUserId(@RequestParam("userId") int userId){
        return blogService.getAllBlogsByUserId(userId);
    }

    @PutMapping("/{blogId}/update")
    public Blog updateBlog(@RequestParam("body") String body, @RequestParam("userId") int userId, @PathVariable("blogId") int blogId) throws Exception {
        return blogService.update(body, userId, blogId);
    }
}
