package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dto.request.filter.CommentFilterRequest;
import com.example.accessingdatamysql.dto.request.CreateCommentRequest;
import com.example.accessingdatamysql.dto.request.UpdateCommentRequest;
import com.example.accessingdatamysql.dto.response.CommentResponseDto;
import com.example.accessingdatamysql.exception.ResourceNotFoundException;
import com.example.accessingdatamysql.model.Comment;
import com.example.accessingdatamysql.model.Document;
import com.example.accessingdatamysql.model.User;
import com.example.accessingdatamysql.repository.CommentRepository;
import com.example.accessingdatamysql.repository.DocumentRepository;
import com.example.accessingdatamysql.repository.RoleRepository;
import com.example.accessingdatamysql.repository.UserRepository;
import com.example.accessingdatamysql.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
