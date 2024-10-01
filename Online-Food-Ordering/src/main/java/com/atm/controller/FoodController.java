package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Food;
import com.atm.model.UserEntity;
import com.atm.response.MessageResponse;
import com.atm.serviceimp.FoodServiceImpl;
import com.atm.serviceimp.RestaurentServiceImp;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodServiceImpl foodService;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private RestaurentServiceImp restaurentService;
	

	@GetMapping("/search")
	private ResponseEntity<List<Food>> searchFoodsByKeyword(
										@RequestParam String name,
										@RequestHeader("Authorization") String jwt
										) throws Exception
	{
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		List<Food> foods = foodService.searchFoodByKeyword(name);
		
		
		return new ResponseEntity<>(foods,HttpStatus.OK);
	}
	
	@GetMapping("/restaurent/{restaurentId}")
	private ResponseEntity<List<Food>> getRestaurentFood(
										@RequestParam boolean vagitarian,
										@RequestParam boolean seasonal,
										@RequestParam boolean nonveg,
										@PathVariable Long restaurentId,
										@RequestParam String foodCategory,
										@RequestHeader("Authorization") String jwt
										) throws Exception
	{
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		
		List<Food> foods = foodService.getRestaurentsFood(restaurentId, vagitarian, nonveg, seasonal, foodCategory);
		
		
		return new ResponseEntity<>(foods,HttpStatus.OK);
	}
	
}
