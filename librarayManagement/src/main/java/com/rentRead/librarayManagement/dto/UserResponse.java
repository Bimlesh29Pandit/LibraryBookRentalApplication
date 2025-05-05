package com.rentRead.librarayManagement.dto;

import com.rentRead.librarayManagement.enumAll.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
	  private Long id;
	    private String email;
	    private String firstName;
	    private String lastName;
	    private Role role;

}
