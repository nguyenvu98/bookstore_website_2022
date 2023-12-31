package com.datn.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dto.CartDetailDto;
import com.datn.dto.CartDto;
import com.datn.entity.Orders;
import com.datn.entity.Products;
import com.datn.entity.Users;
import com.datn.service.CartService;
import com.datn.service.OrderDetailsService;
import com.datn.service.OrdersService;
import com.datn.service.ProductsService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		Products product = productsService.findById(productId);
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		
		// 1- them moi
		// 2- update:
		//		2.1- cong don
		//		2.2- thay the hoan toan (isReplace = true)
		// 3- delete (update vs quantity = 0)
		
		if (!details.containsKey(productId)) {
			CartDetailDto cartDetailDto = createNewCartDetail(product, quantity);
			details.put(productId, cartDetailDto);
		} else if (quantity > 0) {
			if (isReplace) {
				details.get(productId).setQuantity(quantity);
			} else {
				Integer currentQuantity = details.get(productId).getQuantity();
				Integer newQuantity = currentQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		} else {
			details.remove(productId);
		}
		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}
	
	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalQuantity += cartDetail.getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalPrice += (cartDetail.getPrice() * cartDetail.getQuantity());
		}
		return totalPrice;
	}
	
	private CartDetailDto createNewCartDetail(Products product, Integer quantity) {
		CartDetailDto cartDetailDto = new CartDetailDto();
		cartDetailDto.setProductId(product.getId());
		cartDetailDto.setQuantity(quantity);
		cartDetailDto.setPrice(product.getPrice());
		cartDetailDto.setImgUrl(product.getImgUrl());
		cartDetailDto.setSlug(product.getSlug());
		cartDetailDto.setName(product.getName());
		return cartDetailDto;
	}

	@Transactional(rollbackOn = {Exception.class})
	@Override
	public void insert(CartDto cartDto, Users user, String address, String phone) throws Exception {
		// insert vao bang ORDERS
		Orders order = new Orders();
		order.setUser(user);
		order.setAddress(address);
		order.setPhone(phone);
		
		Orders orderResponse = ordersService.insert(order);
		
		if (ObjectUtils.isEmpty(orderResponse)) {
			throw new Exception("Insert into order table failed");
		}
		
		// insert vao bang ORDER_DETAILS
		for (CartDetailDto cartDetailDto : cartDto.getDetails().values()) {
			cartDetailDto.setOrderId(orderResponse.getId());
			orderDetailsService.insert(cartDetailDto);
			
			// update new quantity cho bang PRODUCTS
			Products product = productsService.findById(cartDetailDto.getProductId());
			Integer newQuantity = product.getQuantity() - cartDetailDto.getQuantity();
			productsService.updateQuantity(newQuantity, product.getId());
		}
	}
}
