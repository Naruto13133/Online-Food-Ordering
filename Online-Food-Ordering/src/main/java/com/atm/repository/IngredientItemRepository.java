package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.IngredientItem;

public interface IngredientItemRepository extends JpaRepository<IngredientItem, Long> {

	public List<IngredientItem> findByRestaurnetId(Long id);
	
}
