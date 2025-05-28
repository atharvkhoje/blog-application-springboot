package com.codewithdurgesh.blog.service;

import java.util.List;

import com.codewithdurgesh.blog.payload.CategoryDto;

public interface CategoryService {

	//create
	
		CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//update
		
		CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

		
		
	//delete
	
		public void deleteCategory(Integer categoryId);

		
	//getbyid
	
		public CategoryDto getbyidCategory(Integer categoryId);

		
	//getall
	
		List<CategoryDto> getallCategory();
	
	
}
