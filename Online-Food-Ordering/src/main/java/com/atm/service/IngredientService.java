package com.atm.service;

import java.util.List;

import com.atm.model.IngredientCategory;
import com.atm.model.IngredientItem;

public interface IngredientService {
	
	public IngredientCategory createIngredientCategory(String name, Long restaurentId) throws Exception;

	public IngredientCategory findIngredientCategoryById(Long Id)throws Exception;
	
	public List<IngredientCategory> findIngredinetCategoryByRestaurentId(Long restaurentId) throws Exception;
	
	public IngredientItem createIngredientItem(String ingredientName, Long restaurentId, Long categoryId) throws Exception;
	
	public List<IngredientItem> findrestaurentIngredients(Long RestaurentId) throws Exception;
	
	public IngredientItem updateStock(Long id)throws Exception;

	
	
}
