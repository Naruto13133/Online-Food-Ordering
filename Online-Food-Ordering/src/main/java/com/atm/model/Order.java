package com.atm.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne 
	private UserEntity customer;
	
	@JsonIgnore
	@ManyToOne
	private Restaurent restaurant;
	
	private Long totalAmount;
	
	private String orderStatus;
	
	private Date CreatedAt;
	
	@ManyToOne
	private Address diliveryAdrress;
	
	@OneToMany
	private List<OrderItem> items;
	
	private int totalItem;
	
	private Long totalPrice;
	
//	private Payment payment;
	
}
