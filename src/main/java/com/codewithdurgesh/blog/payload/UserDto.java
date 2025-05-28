package com.codewithdurgesh.blog.payload;

import java.util.HashSet;
import java.util.Set;

import com.codewithdurgesh.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	
	
	private int id;
	
	@NotEmpty  // check notnull and empty also
	@Size(min = 4, message="username must br min of 4 character")
	private String name;
	
	@Email(message="email not valid")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10, message="password must be min of 3 characters and mazimus of 10 characters")
	// you can use this as your password validator for pattern like 8 digit, one uppercase etc...
	//@Pattern(
		//    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
		  //  message = "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character"
		//)
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	
}
