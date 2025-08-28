package com.enote.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.enote.entity.Category;
import com.enote.repository.CategoryRepository;
import com.enote.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(Category category) {
		
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
	public List<Category> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();
		return categories;
	}


}
