package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.filter.CommentFilterRequest;
import com.example.accessingdatamysql.dto.request.CreateCommentRequest;
import com.example.accessingdatamysql.dto.request.UpdateCommentRequest;
import com.example.accessingdatamysql.dto.response.CommentResponseDto;
import com.example.accessingdatamysql.model.Comment;

import java.util.List;

public interface CommentService {

    public Comment save(CreateCommentRequest request);

    public Comment update(Integer userId, UpdateCommentRequest request);

    public void deleteById(Integer id);

    List<CommentResponseDto> findAllPaging(CommentFilterRequest request);

}
