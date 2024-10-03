package com.atm.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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
	public ResponseEntity<?> createCategory(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		try {
		UserEntity user = userService.findUserByJwtToken(jwt);
		Category category = categoryService.createCategoryForOneRestaurent(req.getName(),req.getId());
		return new ResponseEntity<>(category, HttpStatus.OK);
		}catch(Exception e) {
			if(e instanceof SQLIntegrityConstraintViolationException) {
		return new ResponseEntity<>("You already created the category for all your restaurenst.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
			return new ResponseEntity<>("Something went wrong please contact the help desk!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/category/restaurent")
	public ResponseEntity<List<Category>> getCategoryByRestaurentId(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		//here we use Category.Id field for setting the restaurnt id
		List<Category> category = categoryService.findCategoryByRestaurentId(req.getId());
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	
	@GetMapping("/category/restaurents")
	public ResponseEntity<Category> getRestaurentByCategoryId(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		//here we use Category.Id field for setting the restaurnt id
		Category category = categoryService.findCategoryById(req.getId());
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	
	@GetMapping("/category/getAll")
	public ResponseEntity<List<Category>> getAllCategory(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		//here we use Category.Id field for setting the restaurnt id
		List<Category> category = categoryService.getListOfCategory();
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	
	
	
}
