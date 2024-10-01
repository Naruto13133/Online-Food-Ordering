package com.atm.response;


import com.atm.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

	private String jwt;
	
	private String message;
	
	private Role role;
	
}
