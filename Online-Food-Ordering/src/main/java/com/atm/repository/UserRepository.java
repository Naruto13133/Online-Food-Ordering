package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	public UserEntity findByEmail(String userName);
	
	
	
}
