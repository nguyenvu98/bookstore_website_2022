package com.datn.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.dto.CartDetailDto;
import com.datn.repo.OrderDetailsRepo;
import com.datn.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
	
	@Autowired
	private OrderDetailsRepo repo;

	@Transactional(value = TxType.REQUIRED)
	@Override
	public void insert(CartDetailDto cartDetailDto) {
		repo.insert(cartDetailDto);
	}
}
