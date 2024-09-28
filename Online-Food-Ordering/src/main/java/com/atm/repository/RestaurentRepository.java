package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atm.model.Restaurent;

public interface RestaurentRepository extends JpaRepository<Restaurent, Long> {

	Restaurent findByOwnerId(Long userId);
	
	@Query("SELECT r FROM Restaurent r WHERE lower(r.name) LIKE lower(concat('%',:query,'%')) "
	        + "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
	List<Restaurent> findBySearchQuery(String query); 
	
}
