package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.atm.model.Food;

public interface FoodRepo extends JpaRepository<Food, Long> {
	
	List<Food> findByRestaurentId(Long restaurentId);
	
	@Query("SELECT f FROM Food f  WHERE f.name LIKE %:keyword% OR f.FoodCategory.name LIKE %:keyword% ")
	List<Food> SearchFood(@Param ("keyword") String keyword);

}
