package com.atm.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class CartItem {
			
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@ManyToOne
		@JsonIgnore
		private Cart cart;
		
		@ManyToOne
		private Food food;
	
		private int quantity;
		
		private List<String> ingredients = new ArrayList<>();
		
		private Long totalPrice;
		
		
		
}
