package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payload.ApiResponse;
import com.codewithdurgesh.blog.payload.PostDto;
import com.codewithdurgesh.blog.payload.PostResponse;
import com.codewithdurgesh.blog.service.FileService;
import com.codewithdurgesh.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

 
@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	// create post
	
	@PostMapping("/user/{userid}/category/{categoryid}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userid,@PathVariable Integer categoryid)
	{
		PostDto createpost=this.postService.createPost(postDto, userid, categoryid);
		return new ResponseEntity<PostDto>(createpost,HttpStatus.OK);
	}
	
	
	// get by user
	
	@GetMapping("/user/{userid}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userid)
	{
		List<PostDto> posts = this.postService.getPostsByUser(userid);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	// get by category
	
	@GetMapping("/category/{categoryid}/posts")
	public ResponseEntity<List<PostDto>> getPostsBycategory(@PathVariable Integer categoryid)
	{
		List<PostDto> posts = this.postService.getPostesBycategory(categoryid);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	
	//   GET POST BY ID OLD 
	
	/*
	 * // get post by id
	 * 
	 * @GetMapping("/postById/{postId}") 
	 * public public ResponseEntity<PostDto> getAllPost(@PathVariable Integer postId)
	 * {
	 *   Postdto postById=this.postService.getPostById(postId); 
	 *   return new ResponseEntity<PostDto>(postById,HttpStatus.ok);
	 * 
	 * }
	 */
	
	
	// get post by id new
	
	@GetMapping("/getpostbyid/{postid}")
	public ResponseEntity<PostDto> getpostbyid(@PathVariable Integer postid)
	{
		PostDto allpostbyid=this.postService.getPostById(postid);
		return new ResponseEntity<PostDto>(allpostbyid,HttpStatus.OK);
	}
	
	
	//  	GET ALL POST OLD
	
	/*
	 * // get all post OLD
	 * 
	 * @GetMapping("/allpost") 
	 * public ResponseEntity<List<PostDto>> getAllPost() {
	 * 
	 * List<Postdto> allpost=this.postService.getAllPost(); 
	 * return new ResponseEntity<List<PostDto>>(allpost,HttpStatus.ok);
	 * 
	 * }
	 */
	
	
	
	
	// get all post new
	
	@GetMapping("/getallposts")
	public ResponseEntity<PostResponse> getallpost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir
			
			)
	{
		  PostResponse allpost = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(allpost,HttpStatus.OK);
	}
	
	
	// update
	
	@PutMapping("/update/{postid}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postid)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postid);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.ACCEPTED);
		
	}
	
	
	
	
	// delete
	@DeleteMapping("/delete/{postid}")
	public ApiResponse deletePost(@PathVariable Integer postid)
	{
		this.postService.deletePost(postid);
		return new ApiResponse("post deleted",true);
		
	}
	
	
	// serching
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable ("keywords") String keywords)
	{
		List<PostDto> result = this.postService.searchPOsts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	// post image upload api
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	{
		PostDto postDto = this.postService.getPostById(postId);

		String fileName = this.fileService.uploadImage(path, image);
 		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	// method to serve files
	
	@GetMapping(value="post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
	{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
	
}
