package com.codewithdurgesh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.Security.CustomUserDetailService;
import com.codewithdurgesh.blog.Security.JwtTokenHelper;
import com.codewithdurgesh.blog.exceptions.ApiException;
import com.codewithdurgesh.blog.payload.JwtAuthRequest;
import com.codewithdurgesh.blog.payload.JwtAuthResponse;
import com.codewithdurgesh.blog.payload.UserDto;
import com.codewithdurgesh.blog.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)  throws Exception
	{
		
		
		this.authenticete(request.getUsername(),request.getPassword());
		
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
	
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	
	}

 
	private void authenticete(String username, String password) {
		 
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	 
		try
		{
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e)
		{
			System.out.println("invalid exp " +e);
			
			throw new ApiException("invalid password");
		}
 		 
	}
	
	// register new user api
	@PostMapping("/userRegister")
	public ResponseEntity<UserDto > registeruser(@RequestBody UserDto userDto)
	{
	
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
	
	
}
