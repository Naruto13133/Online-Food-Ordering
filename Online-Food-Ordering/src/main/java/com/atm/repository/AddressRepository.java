package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	public Address findByRestaurentId(Long restaurentId) throws Exception ;

	public boolean existsByRestaurentId(Long Id) ;
	
	public void deleteByRestaurentId(Long restaurntId) throws Exception;
	
	public boolean existsByAddressAndRestaurentIdNot(String adrress, Long restuarentId) throws Exception;
	
	
}
