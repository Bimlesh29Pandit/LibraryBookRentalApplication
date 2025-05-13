package com.rentRead.librarayManagement.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.rentRead.librarayManagement.dto.UserResponse;
import com.rentRead.librarayManagement.enumAll.Role;
import com.rentRead.librarayManagement.service.JwtService;
import com.rentRead.librarayManagement.service.UserService;



//@WithMockUser(username = "admin", roles= {"ADMIN"})
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	MockMvc mockmvc;
	
	@MockitoBean
	UserService userService;
	@MockitoBean
	JwtService jwtService;
	
	UserResponse userResponse1, userResponse2;
	@WithMockUser(username = "admin", roles= {"ADMIN","USER"})
	@Test
	public void getAllUserTest() throws Exception {
		userResponse1 = new UserResponse(1L,"bimlesh","Kumar","bimleshgps@gmail.co",Role.USER);
		userResponse2 = new UserResponse(2L,"Abhijeeet","Kumar","Abhijeet@gamil.com",Role.ADMIN);
		
		List<UserResponse> userResponses =  List.of(userResponse1, userResponse2);
		
		ResponseEntity< List<UserResponse>> response = new ResponseEntity<List<UserResponse>>(userResponses,HttpStatus.OK);
		Mockito.when(userService.getAllUser()).thenReturn(response);
//		System.out.print(response.getBody().size());
		
		mockmvc.perform(get("/users"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(response.getBody().size()))
		.andExpect(jsonPath("$[0].id").value(1L))
		.andExpect(jsonPath("$[1].id").value(2L));
	}
	@WithMockUser(username = "admin", roles= {"ADMIN","USER"})
	@Test
	public void getUserByIdTest() throws Exception {
		userResponse1 = new UserResponse(1L,"bimlesh","Kumar","bimleshgps@gmail.co",Role.USER);
		Long userid = userResponse1.getId();
		ResponseEntity<UserResponse> response = new ResponseEntity<UserResponse>(userResponse1,HttpStatus.OK);
		Mockito.when(userService.getUserById(userid)).thenReturn(response);
		
		mockmvc.perform(get("/users/{userId}",userid))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1L));
		
		
	}
	@Test
	public void authorizationTestForGetUser() throws Exception {
		userResponse1 = new UserResponse(1L,"bimlesh","Kumar","bimleshgps@gmail.co",Role.USER);
		userResponse2 = new UserResponse(2L,"Abhijeeet","Kumar","Abhijeet@gamil.com",Role.ADMIN);
		
		List<UserResponse> userResponses =  List.of(userResponse1, userResponse2);
		
		ResponseEntity< List<UserResponse>> response = new ResponseEntity<List<UserResponse>>(userResponses,HttpStatus.OK);
		Mockito.when(userService.getAllUser()).thenReturn(response);
		
		mockmvc.perform(get("/users")).andExpect(status().isUnauthorized());
	}

}


//@RestController
//@RequestMapping("/users")
//public class UserController {
//	@Autowired
//	UserService userService;
//	@GetMapping
//	public ResponseEntity<List<UserResponse>> getAllUser(){
//		return userService.getAllUser();
//	}
//	
//	@GetMapping("/{userId}")
//	public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
//		return userService.getUserById(userId);
//	}