package com.atm.service;

import java.util.List;

import com.atm.model.Order;
import com.atm.model.UserEntity;
import com.atm.request.OrderRequest;

public interface OrderService {
	
	
	public Order creatOrder(OrderRequest req,String  jwt) throws Exception;
	
	public Order updateOrder(Long orderId, String status)  throws Exception;
	
	public void cancelOrder(Long orderId)  throws Exception;	
	
	public List<Order> gerUserOrder(String jwt)  throws Exception;
	
	public List<Order> getRestaurentOrder(Long RestaurentId, String orderStatus)  throws Exception;

	public Order findOrderById(Long orderId) throws Exception ;
	
}
