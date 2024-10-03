package com.atm.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.IngredientCategory;
import com.atm.model.IngredientItem;
import com.atm.model.Restaurent;
import com.atm.repository.IngredientCategoryRepository;
import com.atm.repository.IngredientItemRepository;
import com.atm.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientItemRepository ingredientItemRepo;
	
	@Autowired
	private IngredientCategoryRepository ingCatRepo;
	
	@Autowired
	private RestaurentServiceImp restaurentSer;
	
	
	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurentId) throws Exception {
		
		IngredientCategory ingredientCategory = new IngredientCategory();
		Restaurent restaurent = restaurentSer.findRestaurentById(restaurentId);
		ingredientCategory.setName(name);
		ingredientCategory.setRestaurent(restaurent);
		
		return ingCatRepo.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientCategory> ingredientCategory = ingCatRepo.findById(Id);
		if(ingredientCategory.isPresent()) {
			return ingredientCategory.get();
		}else {
			throw new Exception("Please provide valid input!");
		}
		
	}

	@Override
	public List<IngredientCategory> findIngredinetCategoryByRestaurentId(Long restaurentId) throws Exception {
		
		restaurentSer.findRestaurentById(restaurentId);
		return ingCatRepo.findByRestaurentId(restaurentId);
	}

	@Override
	public IngredientItem createIngredientItem(String ingredientName, Long restaurentId, Long categoryId)
			throws Exception {
		// TODO Auto-generated method stub
		Restaurent restaurent = restaurentSer.findRestaurentById(restaurentId);
		IngredientCategory ingCat = ingCatRepo.findById(categoryId).get();
		IngredientItem ingredientItem = new IngredientItem();
		ingredientItem.setCategory(ingCat);
		ingredientItem.setName(ingredientName);
		ingredientItem.setRestaurent(restaurent);
		ingCat.getIngredientItems().add(ingredientItem);
		ingCatRepo.save(ingCat);
		return ingredientItemRepo.save(ingredientItem);
	}

	@Override
	public List<IngredientItem> findrestaurentIngredients(Long restaurentId) throws Exception {
		// TODO Auto-generated method stub
		
		return ingredientItemRepo.findByRestaurentId(restaurentId);
	}

	@Override
	public IngredientItem updateStock(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientItem> optionalIngredientsItem = ingredientItemRepo.findById(id);
		if(optionalIngredientsItem.isEmpty()) {
			throw new Exception("Item not found!");
		}
		IngredientItem ingItm = optionalIngredientsItem.get();
		ingItm.setInStock(!ingItm.isInStock());
		return ingredientItemRepo.save(ingItm);	
	}

}
