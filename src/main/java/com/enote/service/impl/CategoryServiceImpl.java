package com.enote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.enote.dto.CategoryDto;
import com.enote.dto.CategoryResponse;
import com.enote.entity.Category;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repository.CategoryRepository;
import com.enote.service.CategoryService;
import com.enote.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//Validation Checking 
		
		validation.categoryValidation(categoryDto);
		
	Category category = mapper.map(categoryDto, Category.class);
	
	if(ObjectUtils.isEmpty(category.getId()))
	{
		category.setIsDeleted(false);
		category.setCreatedOn(new Date());
		category.setCreatedBy(1); // currentUserId must not be null
		
	}
	else {
		updateCategory(category);
	}
		
		
		
		Category saveCategory = categoryRepo.save(category);
		
		if(ObjectUtils.isEmpty(saveCategory))
		{
			return false;
		}
		
		return true;
	}

	private void updateCategory(Category category) {
		
		Optional<Category> findById = categoryRepo.findById(category.getId());
		
		if(findById.isPresent())
		{
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
			
		}
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> categories = categoryRepo.findByIsDeletedFalse();
		
		List<CategoryDto> categoryDtoList = categories.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		
List<CategoryResponse> categoryList = categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		
		
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(()->new ResourceNotFoundException("Category not found with Id "+id));
		
		if(ObjectUtils.isEmpty(category))
		{
//			
//			if (category.getName()==null) {
//				throw new IllegalArgumentException("Name is NULL");
//			}
			category.getName().toUpperCase();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategoryById(Integer id) {
		
		Optional<Category> findByCategory = categoryRepo.findById(id);
		
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			
			return true;
		}
		return false;
	}


}
