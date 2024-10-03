package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Category;
import com.atm.model.IngredientCategory;
import com.atm.model.IngredientItem;
import com.atm.request.IngredientCategoryRequest;
import com.atm.request.IngredientRequest;
import com.atm.serviceimp.IngredientServiceImpl;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

	@Autowired
	private IngredientServiceImpl ingredientSev;
	
	
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngedientCategory(
			@RequestBody IngredientCategoryRequest ingCatReq,
			@RequestHeader("Authorization") String jwt) throws Exception
			{
		IngredientCategory item = ingredientSev.createIngredientCategory(ingCatReq.getName(), ingCatReq.getRestaurentId());
		return new ResponseEntity<>(item,HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<IngredientItem> createIngedientItem(
			@RequestBody IngredientRequest ingReq,
			@RequestHeader("Authorization") String jwt) throws Exception
			{
		IngredientItem item = ingredientSev.createIngredientItem(ingReq.getName(),ingReq.getRestaurentId(),ingReq.getCategoryId());
		
		return new ResponseEntity<>(item,HttpStatus.OK);
	}
	
	@PutMapping("/{id}/stock")
	public ResponseEntity<IngredientItem> updateIngredientStock(
			@PathVariable Long id,@RequestHeader("Authorization") String jwt
			
			) throws Exception
			{
		IngredientItem item = ingredientSev.updateStock(id);
		
		return new ResponseEntity<>(item,HttpStatus.OK);
	}
	
	@GetMapping("/restaurent/{id}")
	public ResponseEntity<List<IngredientItem>> getRestaurentIngredients(
			@PathVariable Long id,@RequestHeader("Authorization") String jwt
			
			) throws Exception
			{
		List<IngredientItem> items = ingredientSev.findrestaurentIngredients(id);
		
		return new ResponseEntity<>(items,HttpStatus.OK);
	}
	
	@GetMapping("/restaurent/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurentCategories(
			@PathVariable Long id,@RequestHeader("Authorization") String jwt
			
			) throws Exception
			{
		List<IngredientCategory> items = ingredientSev.findIngredinetCategoryByRestaurentId(id);
		
		return new ResponseEntity<>(items,HttpStatus.OK);
	}
	
	
	
		
}
