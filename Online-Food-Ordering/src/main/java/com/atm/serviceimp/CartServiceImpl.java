package com.atm.serviceimp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.atm.model.Cart;
import com.atm.model.CartItem;
import com.atm.model.Food;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.CartItemRepository;
import com.atm.repository.CartRepository;
import com.atm.repository.FoodRepo;
import com.atm.request.AddCartItemReq;
import com.atm.service.CartService;

public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private FoodServiceImpl foodServ;
	
	@Override
	public CartItem addItemToCart(AddCartItemReq req, String jwt) throws Exception {
		
		UserEntity user = userService.findUserByJwtToken(jwt);
		Food food = foodServ.findFoodById(req.getFoodId());
		Cart cart = cartRepo.findByCustomerId(user.getId());
		
		
		for(CartItem item : cart.getItems()) {
			if(item.getFood().getName().equals(food.getName())) {
				int newQuantity = item.getQuantity()+req.getQuality();
				return updateCartItemQuantity(cart.getId(),newQuantity);
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setFood(food);
		cartItem.setQuantity(req.getQuality());
		cartItem.setIngredients(req.getIngedients());
		cartItem.setTotalPrice(req.getQuality() * food.getPrice());
		cartItem.setCart(cart);
		
		CartItem savedCartItem = cartItemRepo.save(cartItem);
		
		cart.getItems().add(cartItem);
		cartRepo.save(cart);
		
//		int quantityToAdd = 1; // Or any quantity you want to add

//		List<CartItem> newList = cart.getItems().stream()
//		    .collect(Collectors.collectingAndThen(
//		        Collectors.toList(), 
//		        list -> {
//		            // Check if an item with the same name already exists
//		            boolean itemExists = list.stream()
//		                .anyMatch(item -> item.getFood().getName().equals(food.getName())); // Replace "itemName" with the actual item name
//
//		            if (itemExists) {
//		                // If it exists, find it and increment its quantity
//		                list.stream()
//		                    .filter(item -> item.getFood().getName().equals(food.getName()))
//		                    .findFirst()
//		                    .ifPresent(item -> item.setQuantity(item.getQuantity() + req.getQuality()));
//		                list.
//		            } else {
//		                // If it doesn't exist, create a new CartItem and add it
//		                
//		            	CartItem newItem = new CartItem();
//		            	newItem.set
//		                
//		                list.add(newItem);
//		            }
//
//		            return list;
//		        }
//		    ));
		
		return cartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		// TODO Auto-generated method stub
		Optional<CartItem> cartItem = cartItemRepo.findById(cartItemId);
		if(cartItem.isEmpty()) {
			 throw new Exception("");
		}
		 cartItem.get().setQuantity(cartItem.get().getQuantity()+ quantity);
		return cartItem.get();
	}

	@Override
	public Cart removeItemFromCart(Long CartItemId, String jwt) throws Exception {
		// TODO Auto-generate method stub
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		 Cart cart = cartRepo.findByCustomerId(user.getId());
		 List<CartItem> cartItems = cart.getItems()
				 						.stream()
				 						.filter(l -> !l.getId().equals(CartItemId))
				 						.collect(Collectors.toList());
		 
		 cart.setItems(cart.getItems());
		 cartRepo.save(cart);
		return cart;
	}

	@Override
	public Long calculateCartTotal(Cart cart) throws Exception {
		// TODO Auto-generated method stub
		Long total = 0L;
		for(CartItem item : cart.getItems()) {
			total+= item.getFood().getPrice();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> cart = cartRepo.findById(id);
		if(cart.isEmpty()) {
			throw new Exception("Cart is not present!");
		}
		return cart.get();
	}

	@Override
	public Cart findCartByUserId(String jwt) throws Exception {
		UserEntity user = userService.findUserByJwtToken(jwt);
		Cart cart = cartRepo.findByCustomerId(user.getId());
		return cart;
	}

	@Override
	public Cart clearCart( String jwt) throws Exception {
		UserEntity user = userService.findUserByJwtToken(jwt);
	    Cart cart = cartRepo.findByCustomerId(user.getId());
	    if (cart == null) {
	        throw new Exception("Cart not found for user ID: " + user.getId()); // Or a more specific custom exception
	    }
	    cart.getItems().clear();
	    cart = cartRepo.save(cart); 
	    return cart; 
	}



}
