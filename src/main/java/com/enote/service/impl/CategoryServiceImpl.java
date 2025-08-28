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
import com.enote.repository.CategoryRepository;
import com.enote.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category=new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
	Category category = mapper.map(categoryDto, Category.class);
		
		category.setIsDeleted(false);
		category.setCreatedOn(new Date());
		category.setCreatedBy(1); // currentUserId must not be null
		
		Category saveCategory = categoryRepo.save(category);
		
		if(ObjectUtils.isEmpty(saveCategory))
		{
			return false;
		}
		
		return true;
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
	public CategoryDto getCategoryById(Integer id) {
		
		Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
		
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
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
