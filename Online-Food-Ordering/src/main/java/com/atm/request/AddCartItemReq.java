package com.atm.request;

import java.util.List;

import lombok.Data;


@Data
public class AddCartItemReq {

	private Long foodId;
	private int quality;
	private List<String> ingedients;
}
