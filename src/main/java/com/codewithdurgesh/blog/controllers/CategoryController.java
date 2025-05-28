package com.codewithdurgesh.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payload.ApiResponse;
import com.codewithdurgesh.blog.payload.CategoryDto;
import com.codewithdurgesh.blog.service.CategoryService;

import jakarta.validation.Valid;
 

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	
	@Autowired
	private CategoryService categoryService;
	
	
	// create category
	
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	
	
	// update category
	
	@PutMapping("/updateCategory/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
	{
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	
	
	// delete category

	@DeleteMapping("/deleteCategory/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		 this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully",true),HttpStatus.ACCEPTED);
	}
	
	
	// Get by id
	
	@GetMapping("/getbyIdCategory/{catId}")
	public ResponseEntity<CategoryDto> getbyIdCategory(@PathVariable Integer catId)
	{
		CategoryDto getbyIdCategory = this.categoryService.getbyidCategory(catId);
		return new ResponseEntity<CategoryDto>(getbyIdCategory,HttpStatus.OK);
	}
	
	
	// get all category
	
	
	@GetMapping("/allcategory")
	public ResponseEntity<List<CategoryDto>> getallCategory()
	{
		List<CategoryDto> getallCategory = this.categoryService.getallCategory();
		return ResponseEntity.ok(getallCategory);
		
		// both are right
		//return new ResponseEntity<List<CategoryDto>>(getallCategory,HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	

}
