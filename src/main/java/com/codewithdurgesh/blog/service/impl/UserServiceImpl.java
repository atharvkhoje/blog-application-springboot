package com.codewithdurgesh.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.payload.UserDto;
import com.codewithdurgesh.blog.repositories.RoleRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.service.UserService;
import com.codewithdurgesh.blog.exceptions.*;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	// create new user
	@Override
	public UserDto createUser(UserDto userDto) {
		 
		User user= this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.userToDto(savedUser);
		
	}

	//update user
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		  
		User user=this.userRepo.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		
		 User updateUser  = this.userRepo.save(user);
		 UserDto userDto1 = this.userToDto(updateUser);
		 
		return userDto1;
	}
	//find user by ID
	@Override
	public UserDto getUserById(Integer userId) {
 
		User user=this.userRepo.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User","Id",userId));
		UserDto dto=this.userToDto(user);
		
	//	return this.userToDto(user);
		return dto;
	}
	
	//Get all user 
	@Override
	public List<UserDto> getAllUsers() {
 
		List<User> users = this.userRepo.findAll();
		
//	Start //
		
		// 1.   users.stream()
		//Creates a stream from the list of User entities.
		
		// 2.  .map(user -> this.userToDto(user))
        //Applies the userToDto() method to each User object.
        //This method converts a User entity to a UserDto, usually to avoid sending sensitive data (like passwords).

		//3.  .collect(Collectors.toList())
        //Gathers all converted UserDto objects into a new list.

		//✅ So in short:
        //Converts List<User> → List<UserDto> using Java Stream API
		
//	END	//
		
		// this is using JAVA 8
	   //	List<UserDto> userDto =  users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		
		// this is without using JAVA 8
		
		List<UserDto> userDto=new ArrayList<>();
		
		for(User user:users)
		{
			UserDto dto=this.userToDto(user);
			userDto.add(dto);
		}
	 
		return userDto;
	}

	//delete user
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow( ()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
	}
	
	
	// DTO to USER conversion
	public User dtoToUser(UserDto userDto)
	{
		//User user=new User();
		
		// with model mapper
		User user=this.modelMapper.map(userDto,User.class);
		
/*  this is manually without model mapper
 * 
 * 		
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
*/		
		return user;
 	
	}
	// USER TO DTO conversion
	public UserDto userToDto(User user)
	{
		//UserDto userDto=new UserDto();
		
		// with model mapper
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
/*		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
 
 
 */
		return userDto;
 	
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
 
		User user = this.modelMapper.map(userDto, User.class);
		
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// role to user
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	
	

}
