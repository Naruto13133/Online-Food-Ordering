package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Address;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.AddressRepository;
import com.atm.request.CreateRestaurentRequest;
import com.atm.response.MessageResponse;
import com.atm.service.UserService;
import com.atm.serviceimp.RestaurentServiceImp;
import com.atm.serviceimp.UserServiceImp;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/admin")
public class AdminRestaurentController {

	@Autowired
	private RestaurentServiceImp resService;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private AddressRepository addressRepo;
	
	
	@PostMapping("/create")
	private ResponseEntity<?> createRestaurent(
	        @RequestBody CreateRestaurentRequest createRestaurantReq,
	        @RequestHeader("Authorization") String jwt) throws Exception {

		 try {
			 UserEntity user = userService.findUserByJwtToken(jwt);
			  Restaurent res =  resService.createRestaurent(createRestaurantReq, user);
			  return new ResponseEntity<>(res, HttpStatus.CREATED);
	        } catch (Exception e) {
	            // Log the exception
	            e.printStackTrace(); 

	            // Customize error response based on the exception type
	            if (e instanceof DataIntegrityViolationException) { 
	                return ResponseEntity.status(HttpStatus.CONFLICT)
	                        .body("Error creating restaurant: Please use different Adrress." ); 
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body("An unexpected error occurred.");
	            }
	        }
	    
	    
	    
	}

	@PutMapping("/{id}")
	private ResponseEntity<Restaurent> updateRestaurent(
	        @RequestBody CreateRestaurentRequest createRestaurantReq,
	        @RequestHeader("Authorization") String jwt,
	        @PathVariable Long id) throws Exception {

	    UserEntity user = userService.findUserByJwtToken(jwt);

	    Restaurent res = resService.updateRestaurent(id, createRestaurantReq);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<MessageResponse> deleteRestaurent(
	        @RequestHeader("Authorization") String jwt,
	        @PathVariable Long id) throws Exception {

	    // Validate and find the user based on the JWT token
	    UserEntity user = userService.findUserByJwtToken(jwt);

	    // Ensure the user is authorized to delete the restaurant (optional logic)
	    // e.g., checking if the user owns the restaurant or has admin privileges

	    // Perform the delete operation
	    resService.deleteRestaurent(id);
	    
	    MessageResponse res = new MessageResponse();
	    res.setMessage("Restaurent deleted successfully !");

	    // Return a successful response with no content
	    return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@PutMapping("/{id}/status")
	private ResponseEntity<Restaurent> updateRestaurentStatus(
	        @RequestHeader("Authorization") String jwt,
	        @PathVariable Long id) throws Exception {

	    UserEntity user = userService.findUserByJwtToken(jwt);
	    Restaurent rest = resService.updateRestaurentStatus(id);
	    
	    
	    return new ResponseEntity<>(rest,HttpStatus.OK);}
	    
	
	
	@GetMapping("/user")
	private ResponseEntity<List<Restaurent>> findRestaurentByUserId(
	        @RequestHeader("Authorization") String jwt) throws Exception {

	    UserEntity user = userService.findUserByJwtToken(jwt);
	    List<Restaurent> rest = resService.getRestaurentByUserId(user.getId());
	    return new ResponseEntity<>(rest,HttpStatus.OK);
	}

}
