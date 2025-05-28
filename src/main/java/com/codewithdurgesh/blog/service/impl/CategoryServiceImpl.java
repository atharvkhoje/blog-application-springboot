package com.codewithdurgesh.blog.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payload.CategoryDto;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// create category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addcat = this.categoryRepo.save(cat);
		CategoryDto as = this.modelMapper.map(addcat, CategoryDto.class);
		return as;
	}

	//update category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updatecat = this.categoryRepo.save(cat);
		
		
		return this.modelMapper.map(updatecat, CategoryDto.class);
	}

	
	// delete category
	@Override
	public void deleteCategory(Integer categoryId) {
		 
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		this.categoryRepo.delete(cat);

	}

	
	// get category by id
	@Override
	public CategoryDto getbyidCategory(Integer categoryId) {

		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	
	// get all category
	@Override
	public List<CategoryDto> getallCategory() {
 
		List<Category> category = this.categoryRepo.findAll();
		
		List<CategoryDto> catDto = category.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDto;
	}

}
