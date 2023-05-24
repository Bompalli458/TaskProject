package com.bompalli.taskproject.TaskProject.ServiceImple;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bompalli.taskproject.TaskProject.entity.Users;
import com.bompalli.taskproject.TaskProject.payload.UserDto;
import com.bompalli.taskproject.TaskProject.repository.UserRepository;
import com.bompalli.taskproject.TaskProject.service.UserService;
 
@Service
public class UserServiceIMPL implements UserService {

	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		//userDto is not an entity of users
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user=userDtoToEntity(userDto);
		Users savedUser=userRepos.save(user);
		
		return entityToUserDto(savedUser);
	}
	private Users userDtoToEntity(UserDto userDto) {
		Users users=new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
		
	}
	private UserDto entityToUserDto(Users savedUser) {
		
		UserDto userDto=new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setEmail(savedUser.getEmail());
		userDto.setName(savedUser.getName());
		userDto.setPassword(savedUser.getPassword());
		return userDto;
		
	}
}
