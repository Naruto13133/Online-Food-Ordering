package com.atm.service;

import java.util.List;

import com.atm.model.Category;

public interface CategoryService {

	
	public Category createCategory(String name, Long restaurentId) throws Exception;
	
	public List<Category> findCategoryByRestaurentId(Long userId) throws Exception;
	
	public Category findCategoryById(Long Id) throws Exception;
	
}
