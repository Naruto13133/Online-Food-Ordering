package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atm.model.IngredientItem;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientItem, Long> {
	
	public List<IngredientItem> findByRestaurentId(Long id);
	
}
