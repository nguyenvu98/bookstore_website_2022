package com.datn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.entity.ProductTypes;
import com.datn.entity.Products;
import com.datn.repo.ProductTypeRepo;
import com.datn.service.ProductTypeService;
import com.datn.service.ProductsService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{

	@Autowired
	ProductTypeRepo repo;
	
	@Override
	public List<ProductTypes> findAll() {
		return repo.findAll();
	}

	@Override
	public ProductTypes findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductTypes findBySlug(String slug) {
		// TODO Auto-generated method stub
		return null;
	}


}
