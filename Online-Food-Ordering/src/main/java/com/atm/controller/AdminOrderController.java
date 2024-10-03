package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Order;
import com.atm.request.OrderRequest;
import com.atm.serviceimp.OrderServiceImpl;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private UserServiceImp userService;
	

	
	@GetMapping("/order/restaurent/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestParam Long id,
			@RequestParam(required = false) String order_status,
			@RequestHeader("Athourization") String jwt
			) throws Exception{
		
		List<Order> orders = orderService.getRestaurentOrder(id, order_status);
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	

	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(
					@RequestParam Long orderId,
					@RequestParam String orderStatus,
					@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		userService.findUserByJwtToken(jwt);
		Order order = orderService.updateOrder(orderId,orderStatus);
		return new ResponseEntity<>(order, HttpStatus.OK);
	
	}	
}
