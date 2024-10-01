package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Category;
import com.atm.model.UserEntity;
import com.atm.serviceimp.CategoryServiceImpl;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryService;
	
	@Autowired
	private UserServiceImp userService;
	
	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		Category category = categoryService.createCategory(req.getName(),user.getId());
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@GetMapping("/category/restaurent")
	public ResponseEntity<List<Category>> getRestaurentCategory(
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		List<Category> category = categoryService.findCategoryByRestaurentId(user.getId());
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	
	
	
}
