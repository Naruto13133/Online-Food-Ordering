package com.atm.service;

import java.util.List;

import com.atm.model.Category;
import com.atm.model.Food;
import com.atm.model.Restaurent;
import com.atm.request.createFoodRequest;

public interface FoodService {

	public Food createFood(createFoodRequest req,
			Category category,
			Restaurent res );

	void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurentsFood(Long restaurentId, 
										boolean isVegitarian, 
										boolean isNoveg, 
										boolean isSeasonal, 
										String FoodCetegory) throws Exception ;
	
	public List<Food> searchFoodByKeyword(String keyWord);
	
	public Food findFoodById(Long id) throws Exception;
	
	public Food updateAvailibilityStatus(Long foodId) throws Exception;;
	
}
