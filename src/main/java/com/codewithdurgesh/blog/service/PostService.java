package com.codewithdurgesh.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payload.PostDto;
import com.codewithdurgesh.blog.payload.PostResponse;

 
public interface PostService {

	// create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryid);
	
	
	// update post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete
	void deletePost(Integer postId);
	
	
	// get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String soerBy,String sortDir);
	
	
	// get post by is
	PostDto getPostById(Integer postId);
	
	
	//get all post by category
	List<PostDto> getPostesBycategory(Integer categoryId);
	
	
	// get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	
	// search post
	List<PostDto> searchPOsts(String keyword);
	
	
	// image upload
	
	
	
	
	
	
	
	
	
	
	 
	
}
