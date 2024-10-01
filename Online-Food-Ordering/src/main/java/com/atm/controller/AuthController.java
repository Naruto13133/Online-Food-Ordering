package com.atm.controller;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.config.JwtProvider;

import com.atm.enums.Role;
import com.atm.model.Cart;
import com.atm.model.UserEntity;
import com.atm.repository.CartRepository;
import com.atm.repository.UserRepository;
import com.atm.request.LoginRequest;
import com.atm.response.AuthResponse;
import com.atm.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomUserDetailsService customeUserDetailService;

	@Autowired
	private CartRepository cartRepository;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws Exception{
		
		UserEntity isEmailExist = userRepository.findByEmail(user.getEmail());
		if(isEmailExist != null) {
			throw new Exception("Email already used with other account!");
		}
		String password = passwordEncoder.encode(user.getPassword());
		UserEntity createUser = new UserEntity();
		createUser.setEmail(user.getEmail());
		createUser.setFullName(user.getFullName());
		createUser.setAddresses(user.getAddresses());
		createUser.setFavorites(user.getFavorites());
		createUser.setOrders(user.getOrders());
		createUser.setRole(user.getRole());
		createUser.setPassword(password);
		
		UserEntity savedUser = userRepository.save(createUser);
		Cart cart = new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken( user.getEmail(),createUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());
		
		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	private ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) throws Exception{
		String userName =  req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication = authenticate(userName, password);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		authResponse.setRole(Role.valueOf(role));

		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String userName, String password) throws Exception {
		UserDetails userDetails = customeUserDetailService.loadUserByUsername(userName);
		if(userDetails == null) {
			throw new BadCredentialsException("User not exist with the same email! ");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Password is invalid !");
		}
		
		return UsernamePasswordAuthenticationToken.authenticated(userDetails.getUsername(), null,userDetails.getAuthorities());
	}
	
}
