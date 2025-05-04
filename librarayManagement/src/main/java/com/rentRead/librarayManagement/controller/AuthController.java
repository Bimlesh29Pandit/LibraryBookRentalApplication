package com.rentRead.librarayManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentRead.librarayManagement.dto.UserDto;
import com.rentRead.librarayManagement.dto.UserLoginDto;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.service.UserService;
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
		@Autowired
		UserService userService;
		
		@PostMapping("/register")
		public ResponseEntity< UserDto> registerUser(@RequestBody User user) {
			
			return userService.registerUser(user);
			
		}
		@PostMapping("/login")
		public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLogin){
			return userService.loginUser(userLogin);
		}

	
}
