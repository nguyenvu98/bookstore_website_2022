package com.datn.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.entity.Orders;
import com.datn.repo.OrdersRepo;
import com.datn.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	private OrdersRepo repo;

	@Transactional(value = TxType.REQUIRED)
	@Override
	public Orders insert(Orders order) {
		return repo.saveAndFlush(order);
	}
}
