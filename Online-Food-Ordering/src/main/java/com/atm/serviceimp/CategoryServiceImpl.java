package com.atm.serviceimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.Category;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.CategoryRepository;
import com.atm.repository.RestaurentRepository;
import com.atm.repository.UserRepository;
import com.atm.response.CategoryAndRestaurentResponse;
import com.atm.service.CategoryService;

import jakarta.transaction.Transactional;

@Service
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
	@Transactional
	public List<Category> createCategoryForRestaurentsOfUser(String name, Long userId) throws Exception {
	    List<Restaurent> restaurents = restaurentService.getRestaurentByUserId(userId);
	    List<Category> cats = categoryRepo.findAll();
	    List<Category> categories = new ArrayList<>();
	    

	    for (Category cat : cats) {
	    	for(Restaurent res : restaurents) {
	    	    System.out.println(cat.getName().equalsIgnoreCase(name) && 
//		    			cat.getName().equalsIgnoreCase(res.getCuisineType()) && 
		    			cat.getRestaurentId() == res.getId());
	    	if(
	    			
	    			cat.getName().equalsIgnoreCase(name) && 
//	    			cat.getName().equalsIgnoreCase(res.getCuisineType()) && 
	    			cat.getRestaurentId() == res.getId()) 
	    	{
	    		throw new Exception("Category with this name already exists for a restaurant.");
	    		}
	    	}
	    }
	    
	    
	    	Category category = new Category();
	        category.setName(name);
	        category.setRestaurentId(restaurents.get(0).getId()); // Associate the category with the restaurant
	        categories.add(category);
	     

	    return categoryRepo.saveAll(categories);
	}

	@Override
	public List<Category> findCategoryByRestaurentId(Long restaurentId) throws Exception {
		// TODO Auto-generated method stub
		Restaurent restaurent = restaurentService.findRestaurentById(restaurentId);
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

	@Override
	public Category createCategoryForOneRestaurent(String name, Long restaurentId) throws Exception {
		// TODO Auto-generated method stub
		Restaurent restaurent = restaurentService.findRestaurentById(restaurentId);
		List<Category> cats = categoryRepo.findAll();
		for (Category cat : cats) {
	    		if(cat.getRestaurentId() == restaurent.getId() && 
	    				cat.getName().equalsIgnoreCase(name))
	    		{
	    			throw new Exception("It is alredy present!");			
	    		}
	    	}
		
		Category category = new Category();
		category.setName(name);
		category.setRestaurentId(restaurent.getId());
		return categoryRepo.save(category);
	}

	@Override
	public List<CategoryAndRestaurentResponse> getListOfCategoryWithRestaurent(List<Category> categories)
			throws Exception {
			
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getListOfCategory() throws Exception {
		List<Category> category = categoryRepo.findAll();
		return category;
	}

}
