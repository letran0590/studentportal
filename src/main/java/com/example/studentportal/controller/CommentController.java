package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.filter.CommentFilterRequest;
import com.example.studentportal.dto.request.CreateCommentRequest;
import com.example.studentportal.dto.response.CommentResponseDto;
import com.example.studentportal.model.Comment;
import com.example.studentportal.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@CrossOrigin
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
