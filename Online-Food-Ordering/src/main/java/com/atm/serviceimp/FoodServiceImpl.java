package com.atm.serviceimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.Category;
import com.atm.model.Food;
import com.atm.model.Restaurent;
import com.atm.repository.FoodRepo;
import com.atm.request.createFoodRequest;
import com.atm.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepo foodRepo;
	
	@Override
	public Food createFood(createFoodRequest req, Category category, Restaurent res) {
		Food food = new Food();
		
		food.setDiscription(req.getDescription());
		food.setFoodCategory(req.getCategory());
		food.setImages(req.getImges());
		food.setIngredints(req.getIngredients());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setSeasonal(req.isSeasional());
		food.setVegitarian(food.isAvailable());
		food.setRestaurent(res);
		
		
		return foodRepo.save(food);
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = foodRepo.findById(foodId).get();
		food.setRestaurent(null);
		foodRepo.save(food);

	}

	@Override
	public List<Food> getRestaurentsFood(Long restaurentId, 
											boolean isVegitarian, 
											boolean isNoveg, 
											boolean isSeasonal,
											String FoodCetegory) 
											throws Exception {
		// TODO Auto-generated method stub
		List <Food> foods = foodRepo.findByRestaurentId(restaurentId);
		if(isVegitarian) {
			
			foods = filterByVegetarian(foods,isVegitarian);
			
		}
		if(isNoveg) {
			
			foods = filterByNonveg(foods,isNoveg);
			
		}
		if(isSeasonal) {
			
			foods = filterBySeasonal(foods,isSeasonal);
			
		}
		
		if(FoodCetegory != null && !FoodCetegory.equals("")) {
			foods = filterByFoodCategory(FoodCetegory, foods);
		}
		
		return foods;
	}
		
	@Override
	public List<Food> searchFoodByKeyword(String keyWord) {
		// TODO Auto-generated method stub
		List<Food> foods = new ArrayList<>();
		foods = foodRepo.SearchFood(keyWord);
		return foods;
	}

	@Override
	public Food findFoodById(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		return foodRepo.findById(id).get();
	}

	@Override
	public Food updateAvailibilityStatus(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		
		Food food = foodRepo.findById(foodId).get();
		food.setAvailable(!food.isAvailable());
		return food;
	}

	
	private List<Food> filterByFoodCategory(String foodCetegory, List<Food> foods) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food ->  food.getFoodCategory()!= null && food.getFoodCategory().equals(foodCetegory)).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food -> food.isSeasonal()).collect(Collectors.toList());
	}

	private List<Food> filterByNonveg(List<Food> foods, boolean isNoveg) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food -> !food.isVegitarian()).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarian) {
		
		return foods.stream().filter(food -> food.isVegitarian()).collect(Collectors.toList());
		
	}



	
}
