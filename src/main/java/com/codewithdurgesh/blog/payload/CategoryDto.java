package com.codewithdurgesh.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

	
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message="minimum category title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=4,message="minimum category description is 4")
	private String categoryDescription;
}
