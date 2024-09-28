package com.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.UserEntity;
import com.atm.service.UserService;
import com.atm.serviceimp.UserServiceImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/profile")
	private ResponseEntity<UserEntity> findUserByJwtToken( @RequestHeader("Authorization") String jwt) throws Exception{
		UserEntity user = userService.findUserByJwtToken(jwt);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
}
