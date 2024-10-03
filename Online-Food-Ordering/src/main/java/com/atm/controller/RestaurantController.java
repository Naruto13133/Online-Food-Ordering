package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.dto.RestaurentDto;
import com.atm.model.Category;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.CategoryRepository;
import com.atm.request.CreateRestaurentRequest;
import com.atm.response.MessageResponse;
import com.atm.serviceimp.RestaurentServiceImp;
import com.atm.serviceimp.UserServiceImp;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurentServiceImp resService;

	@Autowired
	private UserServiceImp userService;

	@GetMapping("/search")
	private ResponseEntity<List<Restaurent>> searchRestaurent(
	        @RequestHeader("Authorization") String jwt,
	        @RequestParam String keyword) throws Exception {

	    UserEntity user = userService.findUserByJwtToken(jwt);
	    List<Restaurent> res = resService.searchRestaurent(keyword);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("")
	private ResponseEntity<List<Restaurent>> getAllRestaurent(
	        @RequestHeader("Authorization") String jwt) throws Exception {

	    UserEntity user = userService.findUserByJwtToken(jwt);
	    List<Restaurent> res = resService.getAllRestaurent();

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	private ResponseEntity<Restaurent> findRestaurentById(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {

		UserEntity user = userService.findUserByJwtToken(jwt);
		Restaurent res = resService.findRestaurentById(id);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}



	@PutMapping("/{id}/addtofavorites")
	private ResponseEntity<RestaurentDto> addToFavorite(
			@RequestHeader("Authorization") String jwt, 
			@PathVariable Long id) throws Exception {
		
		UserEntity user = userService.findUserByJwtToken(jwt);
		RestaurentDto dto = resService.addToFavorites(id, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

		@GetMapping("/category")
	public ResponseEntity<List<Restaurent>> getRestaurentByCategory(
			@RequestBody Category req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
			System.out.println(req.toString());
		UserEntity user = userService.findUserByJwtToken(jwt);
		//here we use Category.Id field for setting the restaurnt id
		List<Restaurent> category = resService.findByListOfCategory(req.getName());
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
}
