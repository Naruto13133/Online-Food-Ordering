package com.atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("ResponseENtity",HttpStatus.OK);
	}
	
}
