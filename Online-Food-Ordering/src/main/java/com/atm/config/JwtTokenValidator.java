package com.atm.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.atm.service.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Eager
@Component
public class JwtTokenValidator extends OncePerRequestFilter {
	
	

	
	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt != null) {
			jwt = jwt.substring(7);
			try {
				
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				
				Jws<Claims> c = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
				String email = c.getPayload().get("email",String.class);
				
				String s  = c.getPayload().get("authorities",String.class);
				
				
				
				
				
				System.out.println("authorities>>>>>>>>>>>>>>>>>>>>>>>"+s);
				
//				String email = String.valueOf(c.get("email"));
//				String authorities = String.valueOf(claims.get("authorities"));
				 
				
//				 
				Claims claims = Jwts.parser()
						.verifyWith(key)
						.build()
						.parseSignedClaims(jwt)
						.getPayload();
				
//				String email = String.valueOf(claims.get("email"));
//				String authorities = String.valueOf(claims.get("authorities"));

				List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(s);
				UserDetails userDetail = new User(email, email, grantedAuthorities);
				System.out.println("grantedAuthorities>>>>>>>>>>>>>>>"+grantedAuthorities.toString());
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), null, userDetail.getAuthorities());
				System.out.println("authentication>>>>>>>>>>>>>>>"+authentication.toString());
				System.out.println("SecurityContextHolder.getContext()>>>>>>>>>>>>>"+SecurityContextHolder.getContext().toString());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("SecurityContextHolder.getContext()>>>>>>>>>>>>>"+SecurityContextHolder.getContext().toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new BadCredentialsException("invalid credential ...........");
			}
		}
//		try {
		filterChain.doFilter(request, response);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			filterChain.doFilter(request, response);
//		}
	}

}
