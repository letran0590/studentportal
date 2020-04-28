package com.example.studentportal.service.impl;

import com.example.studentportal.dto.request.filter.CommentFilterRequest;
import com.example.studentportal.dto.request.CreateCommentRequest;
import com.example.studentportal.dto.request.UpdateCommentRequest;
import com.example.studentportal.dto.response.CommentResponseDto;
import com.example.studentportal.exception.ResourceNotFoundException;
import com.example.studentportal.model.Comment;
import com.example.studentportal.model.Document;
import com.example.studentportal.model.User;
import com.example.studentportal.repository.CommentRepository;
import com.example.studentportal.repository.DocumentRepository;
import com.example.studentportal.repository.RoleRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public CommentServiceImpl(final UserRepository userRepository,
                              final RoleRepository roleRepository,
                              final CommentRepository commentRepository,
                              final DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public Comment save(CreateCommentRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Comment comment = new Comment();
        // Check user id
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("userId " + request.getUserId() + " not found"));

        // Check user id
        Document document = documentRepository.findById(request.getDocumentId()).orElseThrow(() -> new ResourceNotFoundException("documentId " + request.getDocumentId() + " not found"));
        comment.setUser(user);
        comment.setDocument(document);
        comment.setText(request.getText());
        comment.setDateComment(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Integer commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("commentId " + commentId + " not found"));
        comment.setText(request.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<CommentResponseDto> findAllPaging(CommentFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getStart(), request.getLimit());
        Page<Comment> comments;
        long total = 0;
        if (null != request.getUserId()) {
            comments = commentRepository.findAllByUser_Id(request.getUserId(), pageable);
        } else if(null != request.getDocumentId()){
            comments = commentRepository.findAllByDocument_Id(request.getDocumentId(), pageable);
        }
        else {
            comments = commentRepository.findAll(pageable);
        }
        total = comments.getTotalElements();
        List<CommentResponseDto> responseDtos;
        if (total > 0) {
            responseDtos = comments.stream().map(comment -> CommentResponseDto.fromComment(comment)).collect(Collectors.toList());
            return responseDtos;
        } else {
            return new ArrayList<>();
        }
    }
}
