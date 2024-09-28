package com.atm.request;

import java.time.LocalDate;
import java.util.List;

import com.atm.model.Address;
import com.atm.model.ContactInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurentRequest {

	private Long id;
	private String name;
	private String discription;
	private String cuisineType;
	private Address address;
	private ContactInformation constactInfo;
	private String opinghours;
	private List<String> images;
	private LocalDate registrationDate;
}
