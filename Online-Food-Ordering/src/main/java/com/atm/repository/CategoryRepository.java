package com.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findByRestaurentId(Long id);
 
	public List<Category> findByName(String name) throws Exception;
	
	public void deleteByRestaurentId(Long restaurnetId)  throws Exception;
}
