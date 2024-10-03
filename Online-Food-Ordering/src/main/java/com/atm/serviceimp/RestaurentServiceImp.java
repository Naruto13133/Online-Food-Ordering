package com.atm.serviceimp;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.Request;
import org.hibernate.boot.model.source.internal.hbm.CommaSeparatedStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.atm.dto.RestaurentDto;
import com.atm.model.Address;
import com.atm.model.Category;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.AddressRepository;
import com.atm.repository.CategoryRepository;
import com.atm.repository.RestaurentRepository;
import com.atm.repository.UserRepository;
import com.atm.request.CreateRestaurentRequest;
import com.atm.service.RestaurentService;

import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;

@Component
public class RestaurentServiceImp implements RestaurentService {

	@Autowired
	private RestaurentRepository restaurentRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	
	
	
	
	@Override
	 @Transactional
	public Restaurent createRestaurent(CreateRestaurentRequest req, UserEntity user) {
		
		Category category = new Category();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+req.getAddress().toString());
		
		
		
		Restaurent restaurent = new Restaurent();
		restaurent.setCuisineType(req.getCuisineType());
		restaurent.setConstactInformantion(req.getConstactInfo());
		restaurent.setAddress(req.getAddress());
		restaurent.setDiscription(req.getDiscription());
		restaurent.setName(req.getName());
		restaurent.setOpeningHourse(req.getOpinghours());
		restaurent.setRegistrationDate(LocalDateTime.now());
		restaurent.setOwner(user);
		
//		restaurent.getCuisineType().setName(req.getCuisineType());
		
		Restaurent rest = new Restaurent();
		
		try {
		rest =restaurentRepo.save(restaurent);
		

		Address address = new Address();
		address = addressRepo.findById(rest.getAddress().getId()).get();
		address.setRestaurentId(rest.getId());
		address.setAddress(req.getAddress().getAddress());
		
		addressRepo.save(address);
		
		
		category.setRestaurentId(rest.getId());
		category.setName(rest.getCuisineType());
		
		categoryRepo.save(category);
		System.out.println(rest);
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		return restaurent;
	}

	@Override
	public Restaurent updateRestaurent(Long restaurentId, CreateRestaurentRequest updatedRestaurent) throws Exception {
	    Optional<Restaurent> existingRestaurentOpt = restaurentRepo.findById(restaurentId);

//	    Address address = addressRepo.findByRestaurentId(restaurentId);
	    List<Category> cats = categoryRepo.findByRestaurentId(restaurentId);
	    List<String> categoryNames = cats.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
	    String commaSeparatedNames = String.join(", ", categoryNames);
	   boolean changesOccured = false;
	    
	    	if (!existingRestaurentOpt.isPresent()) {
	    		throw new Exception("Restaurant not found.");
	    	}
	    Restaurent existingRestaurent = existingRestaurentOpt.get();
	    	// Update the restaurant fields only if the provided value is not null and different from the current one
	    	if (updatedRestaurent.getName() != null && !updatedRestaurent.getName().equals(existingRestaurent.getName())) {
	    		existingRestaurent.setName(updatedRestaurent.getName());
	    		changesOccured = true;
	    	}

	    	if (updatedRestaurent.getDiscription() != null && !updatedRestaurent.getDiscription().equals(existingRestaurent.getDiscription())) {
	    		existingRestaurent.setDiscription(updatedRestaurent.getDiscription());
	    		changesOccured = true;
	    	}

	    	if (updatedRestaurent.getCuisineType() != null && !updatedRestaurent.getCuisineType().contains(commaSeparatedNames)) {
	    		existingRestaurent.setCuisineType(updatedRestaurent.getCuisineType());
	    		int i = -1;
	    		for(Category c : cats) {
	    			if(!c.getName().equals(updatedRestaurent.getCuisineType()) ){
	    				break; 
	    			}else {
	    				i++;
	    			}
	    		}
	    		System.out.println(cats.size());
	    		Category category = new Category();
	    		if(cats.size() == i+1) {
	    			category.setId(restaurentId);
	    			category.setName(updatedRestaurent.getCuisineType());
	    			categoryRepo.save(category);
	    		}
	    		else if(i > -1) {
	    			category = cats.get(i);
	    			category.setName(updatedRestaurent.getCuisineType());
	    			categoryRepo.save(category);
	    		}
	    		changesOccured = true;
	    	}
	    	if (updatedRestaurent.getOpinghours() != null && !updatedRestaurent.getOpinghours().equals(existingRestaurent.getOpeningHourse())) {
	    		existingRestaurent.setOpeningHourse(updatedRestaurent.getOpinghours());
	    		changesOccured = true;
	    	}
	    	if (updatedRestaurent.getImages() != null && !updatedRestaurent.getImages().equals(existingRestaurent.getImages())) {
	    		existingRestaurent.setImages(updatedRestaurent.getImages());
	    		changesOccured = true;
	    	}
//	    	
//	    	System.out.println(updatedRestaurent.getAddress() != null &&
//	    		!updatedRestaurent.getAddress()
//	    		.getAddress()
//	    		.equalsIgnoreCase(address.getAddress()));
	    	// Update the address if it's provided and different

	    	Address existingAddress = addressRepo.findByRestaurentId(restaurentId);	    	
	    	if (updatedRestaurent.getAddress() != null) {
	    		
	    		    // ... other code ...

	    		    if (updatedRestaurent.getAddress() != null && 
	    		        !updatedRestaurent.getAddress().getAddress().equalsIgnoreCase(existingAddress.getAddress())) {

	    		        // Check if the new address already exists for another restaurant
	    		        if (addressRepo.existsByAddressAndRestaurentIdNot(
	    		                updatedRestaurent.getAddress().getAddress(), restaurentId)) {
	    		            throw new Exception("Address already exists for another restaurant."); 
	    		        }

	    		        existingAddress.setAddress(updatedRestaurent.getAddress().getAddress());
	    		        addressRepo.save(existingAddress);
	    		        changesOccured = true;
	    		    }
	    	    // ... update other address fields if needed ...
	    	}
	    	// Update the contact information if it's provided and different
	    	if (updatedRestaurent.getConstactInfo() != null && !updatedRestaurent.getConstactInfo().equals(existingRestaurent.getConstactInformantion())) {
	    		existingRestaurent.setConstactInformantion(updatedRestaurent.getConstactInfo());
	    		changesOccured = true;
	    	}
	    	// Save and return the updated restaurant
	    	if(changesOccured == true)
	    		return restaurentRepo.save(existingRestaurent);
	    	else
	    		throw new Exception("Nothing needs to be change!");
	}


	
    // Delete a restaurant by ID
    @Override
    @Transactional
    public void deleteRestaurent(Long restaurentId) throws Exception {
    	Restaurent existingRestaurentOpt = new Restaurent();
    	try {
    	 existingRestaurentOpt = restaurentRepo.findById(restaurentId).get();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        if (existingRestaurentOpt == null) {
            throw new Exception("Restaurant not found.");
        }
        categoryRepo.deleteByRestaurentId(restaurentId);
        if(addressRepo.existsByRestaurentId(restaurentId)) {
        	System.out.println(addressRepo.findByRestaurentId(restaurentId));
        	addressRepo.deleteByRestaurentId(restaurentId);
        }
        restaurentRepo.delete(existingRestaurentOpt);
    }

	@Override
    public List<Restaurent> getAllRestaurent() {
        return restaurentRepo.findAll(); // Return the list of all restaurants
    }

	@Override
	public List<Restaurent> searchRestaurent(String query) {
		// TODO Auto-generated method stub
		return restaurentRepo.findBySearchQuery(query);
	}

	@Override
	public Restaurent findRestaurentById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return	restaurentRepo.findById(Id).get();
		
	}

	@Override
	public List<Restaurent> getRestaurentByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return restaurentRepo.findByOwnerId(userId);
	}

	@Override
    public RestaurentDto addToFavorites(Long restaurentId, UserEntity user) throws Exception {
        Restaurent restaurent = findRestaurentById(restaurentId);

        RestaurentDto dto = new RestaurentDto();
        dto.setDescription(restaurent.getDiscription());
        dto.setImages(restaurent.getImages());
        dto.setTitle(restaurent.getName());
        dto.setId(restaurentId);
        
        if (user.getFavorites().contains(restaurent)) {
        	user.getFavorites().remove(dto);
        }
        else user.getFavorites().add(dto);

        userRepo.save(user); // Save the updated user with favorites
        return dto;
    }

	@Override
	public Restaurent updateRestaurentStatus(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		Restaurent res = restaurentRepo.findById(id).get();
		 if (res != null) {
			 res.setOpen(!res.isOpen());
			 
		 } 
			else throw new Exception("Restaurent is not present !");
		
		return restaurentRepo.save(res);
	}

	@Override
	public List<Restaurent> findByListOfCategory(String categoriesString) throws Exception {

	    String[] categoryArray = categoriesString.split(",");
	    List<String> categoryList = Arrays.stream(categoryArray)
	            .map(String::trim)
	            .collect(Collectors.toList());

	    List<Restaurent> restaurants = new ArrayList<>();
	    for (String categoryName : categoryList) {
	        List<Category> categories = categoryRepo.findByName(categoryName); // Find categories by name
	        for (Category category : categories) {
	        	Restaurent c = restaurentRepo.findById(category.getRestaurentId()).get();
	        	restaurants.add(c);
	             // Add the restaurant associated with the category
	        }
	    }

	    return restaurants;
	}
	
	

}
