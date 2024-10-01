package com.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.model.Cart;
import com.atm.model.CartItem;
import com.atm.model.UserEntity;
import com.atm.request.AddCartItemReq;
import com.atm.request.UpdateCartItemReq;
import com.atm.serviceimp.CartServiceImpl;
import com.atm.serviceimp.UserServiceImp;

@RestController
@RequestMapping("/api")
public class CartController {
	
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
	private UserServiceImp userService;
	
	@PutMapping("cart/add")
	public ResponseEntity<CartItem> addItemToCart(
			@RequestBody AddCartItemReq req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		CartItem cartItem=cartService.addItemToCart(req, jwt);
		
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
	}
	
	@PutMapping("cart-item/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(
			@RequestBody UpdateCartItemReq req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuntity());
		
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
	}
	
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(
			@PathVariable Long id ,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		Cart cart=cartService.removeItemFromCart(id , jwt);
		
		return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	
	@DeleteMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		Cart cart=cartService.clearCart(jwt);
		
		return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		Cart cart = cartService.findCartByUserId(jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK) ;
	} 
	
	
	
					
	

}