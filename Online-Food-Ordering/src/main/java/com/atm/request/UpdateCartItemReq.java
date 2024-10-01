package com.atm.request;

import lombok.Data;

@Data
public class UpdateCartItemReq {

	private int quntity;
	
	private Long cartItemId;
	
}
