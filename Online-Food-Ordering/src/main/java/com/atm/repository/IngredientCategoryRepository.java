package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

	public List<IngredientCategory> findByRestaurentId(Long Id) throws Exception;
	
}
