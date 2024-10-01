package com.atm.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.model.Category;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.CategoryRepository;
import com.atm.repository.RestaurentRepository;
import com.atm.repository.UserRepository;
import com.atm.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private RestaurentRepository restaurentRepo;
	
	@Autowired
	private RestaurentServiceImp restaurentService;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	
	
	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurent restaurent = restaurentService.getRestaurentByUserId(userId);
		Category category = new Category();
//		category.setId(restaurentId);
		category.setName(name);
		category.setRestaurent(restaurent);
		return category;
	}

	@Override
	public List<Category> findCategoryByRestaurentId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurent restaurent = restaurentService.getRestaurentByUserId(userId);
		List<Category> categories = categoryRepo.findByRestaurentId(restaurent.getId());
		return categories;
	}
	@Override
	public Category findCategoryById(Long Id) throws Exception {
	    // Attempt to find the category by ID
	    Optional<Category> categoryOptional = categoryRepo.findById(Id);

	    // If the category is found, return it
	    if (categoryOptional.isPresent()) {
	        return categoryOptional.get();
	    } else {
	        // If the category is not found, throw a custom exception
	        throw new Exception("Category not found with ID: " + Id);
	    }
	}

}
