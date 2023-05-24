package com.bompalli.taskproject.TaskProject.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//get the token from user i.e ,header
		String token=getToken(request);
		//check the token valid or not
		if(org.springframework.util.StringUtils.hasText(token) && jwtTokenProvider.isValidToken(token)) {
			//if valid load the user and set authentication.
			String email=jwtTokenProvider.getEmailFromToken(token);
			UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, userDetails,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		filterChain.doFilter(request,response);
	}
	public  String getToken(HttpServletRequest httpServletRequest) {
		String token=httpServletRequest.getHeader("Authorization");
		if(org.springframework.util.StringUtils.hasText(token)&& token.startsWith("Bearer ")) {
			return token.substring(7,token.length());
		}
		return null;
		
	}

}
