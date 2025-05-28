package com.codewithdurgesh.blog.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private int id;
	
 	
	
	private String content;
	
	@ManyToOne
	private Post post;
	
	
	
}
