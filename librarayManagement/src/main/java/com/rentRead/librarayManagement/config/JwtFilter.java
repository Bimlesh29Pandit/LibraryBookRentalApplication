package com.rentRead.librarayManagement.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.rentRead.librarayManagement.service.JwtService;
import com.rentRead.librarayManagement.service.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private ApplicationContext context;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
			System.out.println(token);
			userName = jwtService.extractUserName(token);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			String role = jwtService.extractRole(token);
			System.out.println(role + "1");
			UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(userName);

			if(jwtService.validateToken(token,userDetails)) {
				System.out.println(userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
		}
		
		// we have to pass the link the chain it will connet this filter to next filter which is usernamepasswordauthenticationfilter
		
		filterChain.doFilter(request, response);
		
	}
}