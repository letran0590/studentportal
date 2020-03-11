package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.common.ApiConstant;
import com.example.accessingdatamysql.dto.request.filter.CommentFilterRequest;
import com.example.accessingdatamysql.dto.request.CreateCommentRequest;
import com.example.accessingdatamysql.dto.response.CommentResponseDto;
import com.example.accessingdatamysql.model.Comment;
import com.example.accessingdatamysql.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@RequestMapping(ApiConstant.COMMENT) // This means URL's start with /demo (after Application path)
public class CommentController {
	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/")
	public ResponseEntity<Comment> create(@RequestBody @Valid CreateCommentRequest request){
		return ResponseEntity.ok(commentService.save(request));
	}

	@GetMapping("/filter")
	public List<CommentResponseDto> filter(CommentFilterRequest request){
		return commentService.findAllPaging(request);
	}
}
