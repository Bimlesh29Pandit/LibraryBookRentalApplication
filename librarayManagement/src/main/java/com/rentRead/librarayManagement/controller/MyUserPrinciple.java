package com.rentRead.librarayManagement.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.rentRead.librarayManagement.model.User;

public class MyUserPrinciple implements UserDetails {
	@Autowired
	User user;
	
	public MyUserPrinciple(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
//		System.out.println(user.getRole().name() +"2");
		return List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()));

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	

}
