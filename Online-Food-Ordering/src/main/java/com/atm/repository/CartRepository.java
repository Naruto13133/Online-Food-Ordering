package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public Cart findByCustomerId(Long id) throws Exception;

	
	
}
