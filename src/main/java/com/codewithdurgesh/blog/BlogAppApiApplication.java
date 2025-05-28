package com.codewithdurgesh.blog;

import org.springframework.boot.SpringApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.repositories.RoleRepo;
import com.zaxxer.hikari.HikariDataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class BlogAppApiApplication// implements CommandLineRunner
{

	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		
		SpringApplication.run(BlogAppApiApplication.class, args);
		
	 
		
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	
	  public void run(String... args) throws Exception {
	  System.out.println(this.passwordEncoder.encode("xyz"));
	  try
		{
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1 = new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
	  
	  }
	 
	 
}

