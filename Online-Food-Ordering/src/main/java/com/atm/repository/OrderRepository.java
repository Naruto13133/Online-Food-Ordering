package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findByCustomerId(Long userId) throws Exception;
	
	public List<Order> findByRestaurantId(Long userId) throws Exception;
	
}
