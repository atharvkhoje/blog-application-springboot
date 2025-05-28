package com.codewithdurgesh.blog.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
 
		
		//  1. get token
		String requestToken=request.getHeader("Authorization");
		// o/p -> Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdGhhcnZAZ21haWwuY29tIiwiaWF0IjoxNjg1MjI0MDY1LCJleHAiOjE2ODUyNDQ4NjV9.6yn...

		
		
		// token start with Bearer 252342342
		if(requestToken!=null)
		{
			System.out.println(requestToken);
		}
		else
		{
			System.out.println("not getting");
		}
		
		System.out.println("i am request token "+requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer "))
		{
		
			token = requestToken.substring(7);
			//o/p -> eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdGhhcnZAZ21haWwuY29tIiw...

			
			try {
			username = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("unable to get token "+e);
			}
			catch(ExpiredJwtException e1)
			{
				System.out.println("jwt token has expired "+e1);
			}
			catch(MalformedJwtException e2)
			{
				System.out.println("invalid jwt "+e2);
			}
			
		}
		else
		{
			System.out.println("JwtToken does not begin with Bearer");
		}
		
		
		// once we get the token now we validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				
				// sab kuch sahi chal raha hai
				// authentication karna hai
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			}
			else
			{
				System.out.println("invalid jwt token");
			}
		}
		
		else
		{
			System.out.println("username is null or context is not null");
		}
		
		
		
		filterChain.doFilter(request, response);
		
		
 
		
	}
}
