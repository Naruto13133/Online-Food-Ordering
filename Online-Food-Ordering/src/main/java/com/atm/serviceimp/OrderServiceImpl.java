package com.atm.serviceimp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.Cart;
import com.atm.model.CartItem;
import com.atm.model.Order;
import com.atm.model.OrderItem;
import com.atm.model.Restaurent;
import com.atm.model.UserEntity;
import com.atm.repository.AddressRepository;
import com.atm.repository.OrderRepository;
import com.atm.repository.RestaurentRepository;
import com.atm.repository.UserRepository;
import com.atm.request.OrderRequest;
import com.atm.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	public OrderRepository orderRepo;
	
	@Autowired
	private RestaurentServiceImp restaurentServ;
	
	@Autowired
	private UserServiceImp userService;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartServiceImpl cartServ;
	
	
	
	@Override
	public Order creatOrder(OrderRequest req, String jwt) throws Exception {
		UserEntity user = userService.findUserByJwtToken(jwt);
		addressRepo.save(req.getDeliveryAddress());
		Restaurent restaurent = restaurentServ.findRestaurentById(req.getRestaurentId()) ;
		
		Order order = new Order();
		order.setCustomer(user);
		order.setDiliveryAdrress(req.getDeliveryAddress());
		if(!user.getAddresses().contains(req.getDeliveryAddress())) {
			user.getAddresses().add(req.getDeliveryAddress());
			userRepo.save(user);
		}
			order.setCreatedAt(new Date());
			order.setOrderStatus("pending");
			order.setRestaurant(restaurent);
			List<OrderItem> orderItems = new ArrayList<>(); 
			
			Cart cart = cartServ.findCartByUserId(jwt) ;
			List<CartItem> cartItems =  cart.getItems();
			for(CartItem item: cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setFood(item.getFood());
				orderItem.setIngredients(item.getIngredients());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setTotalPrice(item.getTotalPrice());
				orderItems.add(orderItem);
			}
			Long totalPrice = cartServ.calculateCartTotal(cart);
			order.setItems(orderItems); 
			order.setTotalPrice(totalPrice);
			
			Order orderSaved = orderRepo.save(order);
		
		
		return null;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		Order order = findOrderById(orderId);
		if(order.getOrderStatus().equalsIgnoreCase("OUT_FOR_DELIVERY") || 
				order.getOrderStatus().equalsIgnoreCase("DELIVERED") || 
				order.getOrderStatus().equalsIgnoreCase("PENDING") ) {
			
			order.setOrderStatus(orderStatus);
			return orderRepo.save(order); 
			
		}else {
			
			throw new  Exception("pelase give valid order status");	
			}
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		
	Order order = findOrderById(orderId);
		orderRepo.deleteById(order.getId());
	}

	@Override
	public List<Order> gerUserOrder(String jwt) throws Exception {
		// TODO Auto-generated method stub
		UserEntity user = userService.findUserByJwtToken(jwt);
		
		return orderRepo.findByCustomerId(user.getId());
	}

	@Override
	public List<Order> getRestaurentOrder(Long RestaurentId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		
		List<Order> orders = orderRepo.findByRestaurantId(RestaurentId);
		if(orderStatus == null )
		{return orders;}
		else{
			return orders.stream()
					.filter(o -> o.getOrderStatus()
							.equalsIgnoreCase(orderStatus))
					.collect(Collectors.toList());
		}
}


	@Override
	public Order findOrderById(Long orderId) throws Exception {
		
		Order order = orderRepo.findById(orderId).get();
		
		return order;
	}

}
