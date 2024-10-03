package com.atm.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Restaurent {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	private String cuisineType;
	
//	@PrimaryKeyJoinColumn()
	@NotFound(action = NotFoundAction.EXCEPTION)
	@ManyToOne(cascade = CascadeType.PERSIST)
	private UserEntity owner;
	
	private String name;
	
	private String discription;
	
	@NotFound(action = NotFoundAction.EXCEPTION)
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ContactInformation constactInformantion;
	
	private String openingHourse;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
	@ElementCollection
	@Column(length = 1000)
	private List<String> images;
	
	
	private LocalDateTime registrationDate;
	
	private boolean open;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurent" , cascade = CascadeType.ALL )
	private List<Food> foods = new ArrayList<>() ;
	
	
	
}
