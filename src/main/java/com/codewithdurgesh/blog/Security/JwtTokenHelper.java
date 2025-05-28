package com.codewithdurgesh.blog.Security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtTokenHelper {

	// jwt token validity in miliseconds that token will br valid up to
	public static final long JWT_TOKEN_VALIDITY= 5 * 60 * 60;
	
	//
	private String secret="ETVCTDHROHCBFHDBCJIGNVJDYSBFBBCH";
	
	
	
	
	// this execute 3 in code
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	
	// this execute 4 in code
    // retrieve username from jwt token
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}
	// It’s short for this lambda:      Claims::getSubject
    //  claims -> claims.getSubject()
	
	
	// this execute 5 in code
		public<T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
		{
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		
 
		// this execute 6 in code
		// for retrieveing any information from token we will need the secret key
		private Claims getAllClaimsFromToken(String token)
		{
			return Jwts
					.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		}
		
		
		// this execute 7 in code
		//check if the token has expired
		private Boolean isTokenExpired(String token)
		{
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
	
	
	// this execute 8 in code
	// retrive expiration date from jwt token
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
 
	// this execute 1 in code
	// generate token for user
	public String generateToken(UserDetails userDetails)
	{
		Map<String, Object> claims = new HashMap<>();
		System.out.println("i am helper claims 82.  "+claims);
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	
	
	// while creating the token - 
	// 1. define clamis of the token, like User, Expiration, Subject, and the ID
	// 2. sign the jwt using the HS512 algorithm and secret key.
	 //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
	
	
	
	
	// this execute 2 in code
	// it will return token
	 private String doGenerateToken(Map<String, Object> claims, String subject) {
    	
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(subject)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

	 
	 
	 
     
	
	
	
	
	
	
	
	
	
	
}
