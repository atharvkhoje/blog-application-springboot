package com.codewithdurgesh.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
//import java.awt.print.Pageable;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payload.PostDto;
import com.codewithdurgesh.blog.payload.PostResponse;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	// create post
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryid) {
 
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		
		Category category=this.categoryRepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("Category", "category is", categoryid));
		
		
		Post post =this.modelmapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelmapper.map(newPost, PostDto.class);
		
		
	}
	
	// update post

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
 
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedpost = this.postRepo.save(post);
		return this.modelmapper.map(updatedpost, PostDto.class);
		
 	}

	// delete post
	
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postId));
		this.postRepo.delete(post);
	}

	
	
	// get all post old
	
	/*
	 * @override public List<PostDto> getAllPost() {
	 * 
	 * List<Post> allpost = this.postRepo.findAll();
	 * 
	 * List<PostDto> postDto = allPosts.stream().map((post)->
	 * this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	 * 
	 * return postDto;
	 * 
	 * }
	 */
	
	
	
	// get all post new
	
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
 
		 Sort sort=null;
		
		 if(sortDir.equalsIgnoreCase("asc"))
		 {
		    sort=Sort.by(sortBy).ascending();	 
		 }
		 else
		 {
			 sort=Sort.by(sortBy).descending();
		 }
		
 		// we apply descending we can also apply ascending
		Pageable p =PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost= this.postRepo.findAll(p);
		
		List<Post> allpost = pagePost.getContent();
		
		List<PostDto> postDto = allpost.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse pResponse = new PostResponse();
		
		pResponse.setContent(postDto);
		pResponse.setPageNumber(pagePost.getNumber());
		pResponse.setPageSize(pagePost.getSize());
		pResponse.setTotalElements(pagePost.getTotalElements());
		pResponse.setTotalPages(pagePost.getTotalPages());
		pResponse.setLastPage(pagePost.isLast());
		
		
		return pResponse;
	}
	
	
	
	
	/*
	 * // get post by id old
	 * 
	 * @override public PostDto getPostById(Integer postId) {
	 * 
	 * Post post = this.postRepo.findById(postId).orElseThrow(()-> new
	 * Resourcenotfoundexception("post","post id",postId)); 
	 * return this.modelMapper.map(post,PostDto.class);
	 * 
	 * }
	 */
	
	

	// get post by id new
	
	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postId));
		return this.modelmapper.map(post, PostDto.class);
	}

	// get post by category
	
	@Override
	public List<PostDto> getPostesBycategory(Integer categoryId) {
 
		Category cat =	this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDto = posts.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
			
		return postDto;
	}

	
	// get post by user
	
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
 
		User user =	this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDto =posts.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	// search post
	
	@Override
	public List<PostDto> searchPOsts(String keyword) {
 
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
