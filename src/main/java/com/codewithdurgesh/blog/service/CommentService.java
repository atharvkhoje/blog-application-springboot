package com.codewithdurgesh.blog.service;

import com.codewithdurgesh.blog.payload.CommentDto;

public interface CommentService {

	
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
}
