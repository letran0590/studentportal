package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.common.ApiConstant;
import com.example.accessingdatamysql.dto.request.CreateUserRequest;
import com.example.accessingdatamysql.dto.request.RoleRequest;
import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
import com.example.accessingdatamysql.service.RoleService;
import com.example.accessingdatamysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j // This means that this class is a Controller
@RequestMapping(ApiConstant.ROLE) // This means URL's start with /demo (after Application path)
public class RoleController {
	private final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping("/")
	public ResponseEntity<Role> create(@RequestBody @Valid RoleRequest request){
		return ResponseEntity.ok(roleService.save(request));
	}
}
