package com.codewithdurgesh.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payload.UserDto;
import com.codewithdurgesh.blog.service.UserService;

import jakarta.validation.Valid;

import com.codewithdurgesh.blog.payload.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST - create user
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
  
		UserDto createUserDto =  this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	
	//PUT - update user
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
	{
		UserDto update = this.userService.updateUser(userDto, userId);
 	
		return ResponseEntity.ok(update);
		
	}
	
	// Admin Specific API means only admin can delete user
	//DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId)

	{
		this.userService.deleteUser(userId);
		
		return new ResponseEntity(new ApiResponse("user deleted",true),HttpStatus.OK);
		
	}
	
	//GET - Get All User
	@GetMapping("/AllUser")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		List<UserDto> getall=this.userService.getAllUsers();
		
		return new ResponseEntity<>(getall,HttpStatus.OK);
				
			// this is OR	
		//return ResponseEntity.ok(this.userService.getAllUsers());
		
	}
	
	
	//GET - Get one User by id
		@GetMapping("/AllUser/{userId}")
		public ResponseEntity<UserDto> getAllUsers(@PathVariable Integer userId)
		{
		
			UserDto userDto = this.userService.getUserById(userId);
		    return new ResponseEntity<>(userDto, HttpStatus.OK);
			
			//return ResponseEntity.ok(this.userService.getUserById(userId));
			
		}
		
	
	
	
}
