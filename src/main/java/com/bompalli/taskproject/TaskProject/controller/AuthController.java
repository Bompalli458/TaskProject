 package com.bompalli.taskproject.TaskProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bompalli.taskproject.TaskProject.payload.JwtAuthResponse;
import com.bompalli.taskproject.TaskProject.payload.LoginDTO;
import com.bompalli.taskproject.TaskProject.payload.UserDto;
import com.bompalli.taskproject.TaskProject.security.JwtTokenProvider;
import com.bompalli.taskproject.TaskProject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider ;
	
	//post store the user in db
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDTO loginDto){
		Authentication authentication=
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
						);
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtTokenProvider.generateToken(authentication);//generation of token
		
		return ResponseEntity.ok(new JwtAuthResponse(token));
		
	}
}
