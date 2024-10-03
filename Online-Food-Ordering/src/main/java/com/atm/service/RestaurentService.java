package com.atm.service;

import java.util.List;

import com.atm.dto.RestaurentDto;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.request.CreateRestaurentRequest;

public interface RestaurentService {

	public Restaurent createRestaurent(CreateRestaurentRequest req, UserEntity user ) ;
	
	public Restaurent updateRestaurent(Long restaurentId, CreateRestaurentRequest updatedRestaurent) throws Exception;
	
	public void deleteRestaurent(Long RestaurentId) throws Exception;
	
	public List<Restaurent> getAllRestaurent();
	public List<Restaurent> searchRestaurent(String query);
	
	public Restaurent findRestaurentById(Long Id) throws Exception;
	
	public List<Restaurent> getRestaurentByUserId(Long userId)  throws Exception;
	
	public RestaurentDto addToFavorites(Long restaurentId, UserEntity user) throws Exception;
	
	public Restaurent updateRestaurentStatus(Long id) throws Exception;

	public List<Restaurent> findByListOfCategory(String categories) throws Exception;

	
}
