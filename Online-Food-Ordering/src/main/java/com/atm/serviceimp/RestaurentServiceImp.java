package com.atm.serviceimp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.atm.dto.RestaurentDto;
import com.atm.model.Address;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.AddressRepository;
import com.atm.repository.RestaurentRepository;
import com.atm.repository.UserRepository;
import com.atm.request.CreateRestaurentRequest;
import com.atm.service.RestaurentService;

@Component
public class RestaurentServiceImp implements RestaurentService {

	@Autowired
	private RestaurentRepository restaurentRepo;
	
	@Autowired
	private AddressRepository AddressRepo;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private UserRepository userRepo;
	
	
	
	
	
	@Override
	public Restaurent createRestaurent(CreateRestaurentRequest req, UserEntity user) {
		
		
		Address address = AddressRepo.save(req.getAddress());
		Restaurent restaurent = new Restaurent();
		restaurent.setAddress(req.getAddress());
		restaurent.setConstactInformantion(req.getConstactInfo());
		restaurent.setDiscription(req.getDiscription());
		restaurent.setName(req.getName());
		restaurent.setOpeningHourse(req.getOpinghours());
		restaurent.setRegistrationDate(LocalDateTime.now());
		restaurent.setOwner(user);
		restaurent.setCuisineType(req.getCuisineType());
		restaurentRepo.save(restaurent);
		return restaurent;
	}

	@Override
	public Restaurent updateRestaurent(Long restaurentId, CreateRestaurentRequest updatedRestaurent) throws Exception {
	    Optional<Restaurent> existingRestaurentOpt = restaurentRepo.findById(restaurentId);

	    if (!existingRestaurentOpt.isPresent()) {
	        throw new Exception("Restaurant not found.");
	    }

	    Restaurent existingRestaurent = existingRestaurentOpt.get();
	    
	    // Update the restaurant fields only if the provided value is not null and different from the current one
	    if (updatedRestaurent.getName() != null && !updatedRestaurent.getName().equals(existingRestaurent.getName())) {
	        existingRestaurent.setName(updatedRestaurent.getName());
	    }

	    if (updatedRestaurent.getDiscription() != null && !updatedRestaurent.getDiscription().equals(existingRestaurent.getDiscription())) {
	        existingRestaurent.setDiscription(updatedRestaurent.getDiscription());
	    }

	    if (updatedRestaurent.getCuisineType() != null && !updatedRestaurent.getCuisineType().equals(existingRestaurent.getCuisineType())) {
	        existingRestaurent.setCuisineType(updatedRestaurent.getCuisineType());
	    }

	    if (updatedRestaurent.getOpinghours() != null && !updatedRestaurent.getOpinghours().equals(existingRestaurent.getOpeningHourse())) {
	        existingRestaurent.setOpeningHourse(updatedRestaurent.getOpinghours());
	    }

	    if (updatedRestaurent.getImages() != null && !updatedRestaurent.getImages().equals(existingRestaurent.getImages())) {
	        existingRestaurent.setImages(updatedRestaurent.getImages());
	    }

	    // Update the address if it's provided and different
	    if (updatedRestaurent.getAddress() != null && !updatedRestaurent.getAddress().equals(existingRestaurent.getAddress())) {
	        Address updatedAddress = AddressRepo.save(updatedRestaurent.getAddress());
	        existingRestaurent.setAddress(updatedAddress);
	    }

	    // Update the contact information if it's provided and different
	    if (updatedRestaurent.getConstactInfo() != null && !updatedRestaurent.getConstactInfo().equals(existingRestaurent.getConstactInformantion())) {
	        existingRestaurent.setConstactInformantion(updatedRestaurent.getConstactInfo());
	    }

	    // Save and return the updated restaurant
	    return restaurentRepo.save(existingRestaurent);
	}


	
    // Delete a restaurant by ID
    @Override
    public void deleteRestaurent(Long restaurentId) throws Exception {
        Optional<Restaurent> existingRestaurentOpt = restaurentRepo.findById(restaurentId);
        
        if (!existingRestaurentOpt.isPresent()) {
            throw new Exception("Restaurant not found.");
        }

        restaurentRepo.delete(existingRestaurentOpt.get());
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
	public Restaurent getRestaurentByUserId(Long userId) throws Exception {
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

}
