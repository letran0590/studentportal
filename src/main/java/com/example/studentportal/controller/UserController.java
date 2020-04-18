package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.*;
import com.example.studentportal.dto.request.filter.UserFilterRequest;
import com.example.studentportal.dto.response.ResponseDto;
import com.example.studentportal.dto.response.UserResponseDto;
import com.example.studentportal.model.User;
import com.example.studentportal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@CrossOrigin
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
	public List<User> filter(UserFilterRequest filterRequest){
		return userService.getListUserByFilter(filterRequest);
	}

	@GetMapping("/findByEmail")
	public User findByEmail(@RequestParam String email){
		return userService.findUserByEmail(email);
	}

	@PostMapping("/updateInfo")
	public ResponseDto<UserResponseDto>  updateUserInfo(@RequestBody @Valid UpdateInfoRequest request){
		UserResponseDto userResponse = userService.updateInfo(request);
		return new ResponseDto<>(userResponse);
	}

	@PostMapping("/updatePassword")
	public ResponseDto<UserResponseDto>  updatePassword(@RequestBody @Valid UpdatePasswordRequest request){
		UserResponseDto userResponse = userService.updatePassword(request);
		return new ResponseDto<>(userResponse);
	}

	@PutMapping("/{userId}/avatar")
	public ResponseEntity<?> addAvatar(@RequestParam("file") MultipartFile file,
										@PathVariable int userId){
		log.debug("Upload avatar!");
		if (file.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}
		User user;
		try {
			user = userService.uploadAvatar(userId, file);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(user);
	}
}
