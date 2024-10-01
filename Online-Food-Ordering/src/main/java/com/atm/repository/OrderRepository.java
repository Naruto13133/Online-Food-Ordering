package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	
	
}
