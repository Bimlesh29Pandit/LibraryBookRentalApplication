package com.rentRead.librarayManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;
import com.rentRead.librarayManagement.dto.UserDto;
import com.rentRead.librarayManagement.dto.UserLoginDto;
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
//	@Autowired
//	private authenticationManager
	@Autowired
	private JwtService jwtService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public ResponseEntity<UserDto> registerUser(User user) {
		// TODO Auto-generated method stub

		user.setEmail(user.getEmail().toLowerCase());

		if(userRepositiroy.existsByEmail(user.getEmail())){
			throw new IllegalArgumentException("Email already exists in the System try with another email or login with the same");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		User user1 = userRepositiroy.save(user);


		UserDto userDto = modelMapper.map(user1, UserDto.class);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);


	}
	public ResponseEntity<String> loginUser(UserLoginDto userLogin) throws AuthenticationException {
		// TODO Auto-generated method stub
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

//		return new ResponseEntity<String>("login Successfull", HttpStatus.ACCEPTED);

	}

}
