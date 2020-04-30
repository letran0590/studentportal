package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.RoleRequest;
import com.example.studentportal.model.Role;
import com.example.studentportal.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j // This means that this class is a Controller
@CrossOrigin
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
