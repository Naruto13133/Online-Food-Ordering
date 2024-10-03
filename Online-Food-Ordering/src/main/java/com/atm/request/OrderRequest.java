package com.atm.request;

import com.atm.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

	private Long restaurentId;
	private Address deliveryAddress;
	
}
