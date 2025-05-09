package com.rentRead.librarayManagement.service;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;
import com.rentRead.librarayManagement.dto.UserLoginDto;
import com.rentRead.librarayManagement.dto.UserRegistrationRequest;
import com.rentRead.librarayManagement.dto.UserResponse;
import com.rentRead.librarayManagement.enumAll.Role;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.repo.UserRepositiory;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	UserResponse userResponse;
	@Mock
	UserRepositiory userRepositiory;
	@Mock
	ModelMapper modelMapper;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtService jwtService;
	@Mock
	UserRegistrationRequest userRegistrationRequest;
	@InjectMocks
	UserService userService;
	@Mock
	BCryptPasswordEncoder encoder ;
	
	User user ;
	UserLoginDto userLoginDto;
	@BeforeEach
	public void setUp() {
		user = new User(1L, "Rashi", "Yadav", "rashi@example.com", "abc", Role.USER, new ArrayList<>());
		userResponse = new UserResponse(1L, "Rashi@example.com","Rashi", "Yadav",Role.USER);
		userRegistrationRequest = new UserRegistrationRequest("Rashi@example.com","R@12345","Rashi", "Yadav",Role.USER);
		userLoginDto = new UserLoginDto("Rashi@example.com", "R@12345");
	}
	
	@Test
	public void registerUserTest() {
		String email = userRegistrationRequest.getEmail().toLowerCase();
		String passwordString = user.getPassword();
		user.setId(null);
		when(userRepositiory.existsByEmail(email)).thenReturn(false);
		when(userRepositiory.save(user)).thenReturn(user);
		when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);
		when(encoder.encode(anyString())).thenReturn(passwordString);
		
		ResponseEntity<UserResponse> response = userService.registerUser(userRegistrationRequest);
		
		Assertions.assertEquals(HttpStatus.CREATED	, response.getStatusCode());
		Assertions.assertEquals(userResponse, response.getBody());
		
	}
	@Test
	public void duplicateRegisterUserTest() {
		String email = userRegistrationRequest.getEmail().toLowerCase();
		String passwordString = user.getPassword();
		user.setId(null);
		when(userRepositiory.existsByEmail(email)).thenReturn(true);
		
		Assertions.assertThrows(IllegalArgumentException.class , ()->userService.registerUser(userRegistrationRequest));
		
	}
	
	@Test
	public void loginUserTest() {
		String email = userRegistrationRequest.getEmail().toLowerCase();
		 Authentication auth = mock(Authentication.class);
		when(userRepositiory.findByEmail(email)).thenReturn(user);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
		when(auth.isAuthenticated()).thenReturn(true);
		when(jwtService.generateToken(email, userRegistrationRequest.getRole())).thenReturn("fake-jwt-token");
		
		ResponseEntity<String> response = userService.loginUser(userLoginDto);
		
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
		Assertions.assertEquals("fake-jwt-token", response.getBody());
		
	}
	@Test
	public void loginUserWithoutRegistrationTest() {
		String email = userRegistrationRequest.getEmail().toLowerCase();
		 Authentication auth = mock(Authentication.class);
		when(userRepositiory.findByEmail(email)).thenReturn(null);
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->userService.loginUser(userLoginDto));
	}
	@Test
	public void loginUserInvalidPasswordTest() {
		String email = userRegistrationRequest.getEmail().toLowerCase();
		 Authentication auth = mock(Authentication.class);
		when(userRepositiory.findByEmail(email)).thenReturn(user);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
		when(auth.isAuthenticated()).thenReturn(false);
		
		ResponseEntity<String> response = userService.loginUser(userLoginDto);
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		Assertions.assertEquals("Invalid Password try again !!!!", response.getBody());
	}

}
