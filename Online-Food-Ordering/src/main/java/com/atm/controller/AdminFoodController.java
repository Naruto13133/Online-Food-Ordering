package com.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Food;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.request.createFoodRequest;
import com.atm.response.MessageResponse;
import com.atm.service.FoodService;
import com.atm.service.UserService;
import com.atm.serviceimp.FoodServiceImpl;
import com.atm.serviceimp.RestaurentServiceImp;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	@Autowired
	private FoodServiceImpl foodService;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private RestaurentServiceImp restaurentService;
	
	@PostMapping
	private ResponseEntity<Food> createFood(
										@RequestBody createFoodRequest req,
										@RequestHeader("Authorization") String jwt
										) throws Exception
	{
		UserEntity user = userService.findUserByJwtToken(jwt);
		Restaurent restaurent = restaurentService.findRestaurentById(req.getRestaurentId());
		Food food = foodService.createFood(req, req.getCategory(), restaurent);
		
		
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	private ResponseEntity<MessageResponse> deleteFood(
										@PathVariable Long id,
										@RequestHeader("Authorization") String jwt
										) throws Exception
	{
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		foodService.deleteFood(id);
		MessageResponse responseMessage = new MessageResponse();
		responseMessage.setMessage("Food deleted successfully!");
		
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	private ResponseEntity<Food> updateFoodAvailablityStatus(
										@PathVariable Long id,
										@RequestHeader("Authorization") String jwt
										) throws Exception
	{
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		Food food = foodService.updateAvailibilityStatus(id);
		
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
	
	
	
	
}
