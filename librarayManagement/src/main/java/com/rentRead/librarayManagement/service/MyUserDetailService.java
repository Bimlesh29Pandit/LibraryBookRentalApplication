package com.rentRead.librarayManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rentRead.librarayManagement.controller.MyUserPrinciple;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.repo.UserRepositiory;

@Component
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepositiory userRepositiory;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepositiory.findByEmail(username);
		if(user == null) {
			System.out.println("404 user not found");
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrinciple(user);
	}

}
