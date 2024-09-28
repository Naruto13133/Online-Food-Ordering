package com.atm.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.config.JwtProvider;
import com.atm.model.UserEntity;
import com.atm.repository.UserRepository;
import com.atm.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepo; 
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public UserEntity findUserByJwtToken(String jwt) throws Exception {
		
		String email = jwtProvider.getEmailFromToken(jwt);
		
		UserEntity user = userRepo.findByEmail(email);
		return user;
	}

	@Override
	public UserEntity findUserByEmail(String email) throws Exception {
		UserEntity user = userRepo.findByEmail(email);
		if(user == null) {
			throw new Exception("User not found!");
		}
		
		return user;
	}

}
