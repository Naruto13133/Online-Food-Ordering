package com.atm.request;

import java.util.List;

import com.atm.model.Category;
import com.atm.model.IngredientItem;

import lombok.Data;

@Data
public class createFoodRequest {

	private String name;
	private String description;
	private Long price;
	
	private Category category;
	private List<String> imges;
	
	private Long RestaurentId;
	private boolean vegetarian;
	private boolean seasional;
	private List<IngredientItem> ingredients ;
}
