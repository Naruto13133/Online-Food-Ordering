package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	
}
