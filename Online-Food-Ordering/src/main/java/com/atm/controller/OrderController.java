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

import com.atm.model.CartItem;
import com.atm.model.Order;
import com.atm.request.OrderRequest;
import com.atm.service.OrderService;
import com.atm.serviceimp.OrderServiceImpl;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderService; 
	
	@Autowired
	private UserServiceImp userService;
	
	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(
					@RequestBody OrderRequest req,
					@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		Order order = orderService.creatOrder(req,jwt);
		return new ResponseEntity<>(order, HttpStatus.OK);
	
	}
	
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestHeader("Athourization") String jwt
			) throws Exception{
		
		List<Order> orders = orderService.gerUserOrder(jwt);
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	

	
	
	
}
