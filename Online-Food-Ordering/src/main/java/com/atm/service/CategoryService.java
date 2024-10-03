package com.atm.service;

import java.util.List;

import com.atm.model.Category;
import com.atm.response.CategoryAndRestaurentResponse;

public interface CategoryService {

	
	public List<Category> createCategoryForRestaurentsOfUser(String name, Long userId) throws Exception ;
	
	public List<Category> findCategoryByRestaurentId(Long userId) throws Exception;
	
	public Category findCategoryById(Long Id) throws Exception;
	
	public Category createCategoryForOneRestaurent(String name, Long restaurentId) throws Exception; 
	
	public List<CategoryAndRestaurentResponse> getListOfCategoryWithRestaurent(List<Category> categories) throws Exception;
	
	public List<Category> getListOfCategory() throws Exception;
	
}
