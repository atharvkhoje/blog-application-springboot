package com.codewithdurgesh.blog.service;

import java.util.List;

import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.payload.UserDto;

public interface UserService {

	
	
	UserDto registerNewUser(UserDto userDto);
	
	// create user
	UserDto createUser(UserDto user);
	
	//update user
	UserDto updateUser(UserDto user,Integer userId);
	
	//get user by id
	UserDto getUserById(Integer userId);
	
	//give all users
	List<UserDto> getAllUsers();
	
	//delete user
	void deleteUser(Integer userId);
	
	
	
	
	
}
