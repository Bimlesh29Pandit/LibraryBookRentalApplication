
package com.rentRead.librarayManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentRead.librarayManagement.dto.UserResponse;
import com.rentRead.librarayManagement.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUser(){
		return userService.getAllUser();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
		return userService.getUserById(userId);
	}
	


}
