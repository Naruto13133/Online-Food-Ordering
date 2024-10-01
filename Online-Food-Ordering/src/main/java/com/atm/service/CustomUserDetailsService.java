package com.atm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.atm.enums.Role;
import com.atm.model.UserEntity;
import com.atm.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User Not with email "+email);
			
		}else {
			Role role = user.getRole();
			List<GrantedAuthority> auth = new ArrayList<>();
			auth.add(new SimpleGrantedAuthority(role.toString()));
			UserDetails test = new User(user.getEmail(), user.getPassword(), auth);
			System.out.println("Test???????????"+test.toString());
			return test;
		}
	}

}
