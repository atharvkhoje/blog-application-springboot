package com.codewithdurgesh.blog.Security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	 
	
	  @Override public void commence(HttpServletRequest request,
	  HttpServletResponse response, AuthenticationException authException) throws
	  IOException, ServletException {
	  
	  response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied");
	  
	  PrintWriter writer = response.getWriter();
	  writer.println("Access denied !! "+authException.getMessage());
	  
	  }
	 
}
