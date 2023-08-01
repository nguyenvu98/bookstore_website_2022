package com.datn.service;

import java.util.List;

import com.datn.entity.ProductTypes;

public interface ProductTypeService {
	List<ProductTypes> findAll();
	ProductTypes findById(Long id);
	ProductTypes findBySlug(String slug);
}
