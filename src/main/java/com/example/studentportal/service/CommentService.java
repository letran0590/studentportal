package com.example.studentportal.service;

import com.example.studentportal.dto.request.filter.CommentFilterRequest;
import com.example.studentportal.dto.request.CreateCommentRequest;
import com.example.studentportal.dto.request.UpdateCommentRequest;
import com.example.studentportal.dto.response.CommentResponseDto;
import com.example.studentportal.model.Comment;

import java.util.List;

public interface CommentService {

    public Comment save(CreateCommentRequest request);

    public Comment update(Integer userId, UpdateCommentRequest request);

    public void deleteById(Integer id);

    List<CommentResponseDto> findAllPaging(CommentFilterRequest request);

}
