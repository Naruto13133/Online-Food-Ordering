package com.atm.config;

import io.jsonwebtoken.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String roles = populateAuthorityItems(authorities);
//		String jwt = Jwts.builder()
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(new Date().getTime()+86400000))
//				.claim("email",auth.getName())
//				.claim("authorities",auth.getAuthorities())
//				.signWith(key)
//				.compact();
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(auth.getAuthorities());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String jwt = Jwts.builder()
		.subject(auth.getName())
		.claim("email",auth.getName())
		.claim("authorities",roles)
		.issuedAt(new Date())
		.expiration(new Date(new Date().getTime()+86400000))
		.signWith(key)
		.compact();
		
		System.out.println("Provider is Called");
		
		
		return jwt;
		
	}
	
	
	public  String getEmailFromToken (String jwt) {
		jwt = jwt.substring(7);
		String email = Jwts.parser()
		.verifyWith(key)
		.build()
		.parseSignedClaims(jwt)
		.getPayload()
		.getSubject();
//		Claims claims = 
//				Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
//		String email = String.valueOf(claims.get("email"));
		return email;
	}
	
	public String populateAuthorityItems(Collection<? extends GrantedAuthority> authorities){
		Set<String> auths = new HashSet<>();
		for(GrantedAuthority auth : authorities) {
			auths.add(auth.getAuthority());
		}
		return String.join(",",auths);
	}
	
}
