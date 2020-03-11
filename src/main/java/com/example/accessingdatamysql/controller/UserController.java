package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.common.ApiConstant;
import com.example.accessingdatamysql.dto.request.CreateUserRequest;
import com.example.accessingdatamysql.dto.request.LoginRequest;
import com.example.accessingdatamysql.dto.request.UpdateUserRequest;
import com.example.accessingdatamysql.dto.request.filter.CommentFilterRequest;
import com.example.accessingdatamysql.dto.response.CommentResponseDto;
import com.example.accessingdatamysql.dto.response.ResponseDto;
import com.example.accessingdatamysql.dto.response.UserResponseDto;
import com.example.accessingdatamysql.model.User;
import com.example.accessingdatamysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@RequestMapping(ApiConstant.USER) // This means URL's start with /demo (after Application path)
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequest request){
		return ResponseEntity.ok(userService.save(request));
	}
	@PostMapping("/login")
	public ResponseDto<UserResponseDto> login(@RequestBody @Valid LoginRequest request){
		UserResponseDto userResponse = userService.login(request);
		return new ResponseDto<>(userResponse);
	}

	@PutMapping("/assignStudents")
	public ResponseEntity<List<User>> assignStudents(@RequestBody @Valid UpdateUserRequest request){
		return ResponseEntity.ok(userService.assignStudents(request));
	}
	@GetMapping("/filter")
	public List<User> filter(){
		return userService.findAll();
	}
}
