package com.atm.model;

import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints  = @UniqueConstraint(columnNames = {"address"}))
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
//	@ManyToOne
//	@JsonIgnore
	private Long restaurentId;
	
	private String address;
	
	
}
