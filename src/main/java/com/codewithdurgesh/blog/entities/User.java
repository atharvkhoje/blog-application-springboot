package com.codewithdurgesh.blog.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails{

 
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Id
		private int id;
		private String name;
		private String email;
		private String password;
		private String about;
		
		
		@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
		private List<Post> posts= new ArrayList<>();
		
		
		@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
		@JoinTable(name="user_role",joinColumns=@JoinColumn(name="user",referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id")
				)
		private Set<Role> roles = new HashSet<>();

 
		@Override
		public boolean isCredentialsNonExpired()
		{
			return true;
		}
		 
		
		@Override
		public boolean isEnabled()
		{
			return true;
		}
		
		
		@Override
		public boolean isAccountNonLocked()
		{
			return true;
		}

		
		/*
		 * @Override public Collection<? extends GrantedAuthority> getAuthorities()
		 *  {  
		 *       TODO Auto-generated method stub return null; 
		 *  }
		 */

		/*
		 * 1. this.roles.stream() Converts the collection roles into a Stream. A Stream
		 * lets you process elements one by one with powerful functional operations
		 * (like map, filter, reduce).
		 * 
		 * 2. .map(role -> new SimpleGrantedAuthority(role.getName())) The .map() method
		 * transforms each element of the stream. Here, for each role in the stream, you
		 * create a new SimpleGrantedAuthority object. role.getName() gets the name of
		 * the role as a string (e.g., "ROLE_ADMIN"). So, each role object is converted
		 * into a SimpleGrantedAuthority object with the role's name.
		 * 
		 * 3. .collect(Collectors.toList()) This collects all the transformed elements
		 * back into a List. So you end up with a List<SimpleGrantedAuthority>. What it
		 * does overall? It takes your user's roles, Converts each role to a
		 * SimpleGrantedAuthority object (which Spring Security understands as an
		 * authority), And collects them into a list.
		 */
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> authories = this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			return  authories;
		}

		

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.email;
		}
		
		
}
