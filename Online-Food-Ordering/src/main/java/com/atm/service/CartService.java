package com.atm.service;

import com.atm.model.Cart;
import com.atm.model.CartItem;
import com.atm.request.AddCartItemReq;

public interface CartService {

	public CartItem addItemToCart(AddCartItemReq req, String jwt) throws Exception;
	
	public Cart removeItemFromCart(Long CartItemId, String jwt) throws Exception;
	
	public Long calculateCartTotal(Cart cart) throws Exception;
	
	public Cart findCartById(Long Id) throws Exception;
	
	public Cart findCartByUserId(String jwt) throws Exception;
	
	public Cart clearCart(String jwt) throws Exception;

	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;
	
}
