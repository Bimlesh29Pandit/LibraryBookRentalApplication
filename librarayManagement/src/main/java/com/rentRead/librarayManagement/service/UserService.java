package com.rentRead.librarayManagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;

import com.rentRead.librarayManagement.dto.UserLoginDto;
import com.rentRead.librarayManagement.dto.UserRegistrationRequest;
import com.rentRead.librarayManagement.dto.UserResponse;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.repo.UserRepositiory;
@Service
public class UserService {

	
	@Autowired
	UserRepositiory userRepositiroy;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public ResponseEntity<UserResponse> registerUser(UserRegistrationRequest userRequest) {
	    // Normalize email
	    String email = userRequest.getEmail().toLowerCase();

	    // Check if email already exists
	    if (userRepositiroy.existsByEmail(email)) {
	        throw new IllegalArgumentException("Email already exists in the system. Try with another email or login instead.");
	    }

	    // Create User entity and map fields manually or with ModelMapper
	    User user = new User();
	    user.setEmail(email);
	    user.setPassword(encoder.encode(userRequest.getPassword()));
	    user.setFirstName(userRequest.getFirstName());
	    user.setLastName(userRequest.getLastName());
	    user.setRole(userRequest.getRole()); // Or whatever default role you assign

	    // Save user
	    User savedUser = userRepositiroy.save(user);

	    // Convert entity to DTO
	    UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);

	    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<String> loginUser(UserLoginDto userLogin) throws AuthenticationException {

		User user = userRepositiroy.findByEmail(userLogin.getEmail().toLowerCase());
		if(user == null) {
			throw new ResourceNotFoundException("Email is not register with use please first register and login again");
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), userLogin.getPassword()));
		if(authentication.isAuthenticated()) {
			
			return new ResponseEntity<String>( jwtService.generateToken(user.getEmail(), user.getRole()),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Invalid Password try again !!!!", HttpStatus.BAD_REQUEST);
		}


	}

	public ResponseEntity<List<UserResponse>> getAllUser() {
		
		List<User> users = userRepositiroy.findAll();
		List<UserResponse> userResponses = users.stream().map(user->modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
		return new ResponseEntity<List<UserResponse>>(userResponses,HttpStatus.OK);
	}

	public ResponseEntity<UserResponse> getUserById(Long userId) {
		User user = userRepositiroy.findById(userId).orElseThrow(()->new ResourceNotFoundException("UserId not found") );
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
	}

}
