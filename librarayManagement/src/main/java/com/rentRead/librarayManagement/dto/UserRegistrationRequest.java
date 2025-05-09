package com.rentRead.librarayManagement.dto;

import com.rentRead.librarayManagement.enumAll.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
	    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

}
