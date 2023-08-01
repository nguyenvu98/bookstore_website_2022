package com.datn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.datn.entity.Products;

public interface ProductsService {
	Page<Products> findAll(int pageSize, int pageNumber) throws Exception;

	List<Products> findAll();
	Products findById(Long id);
	Products findBySlug(String slug);
	void updateQuantity(Integer newQuantity, Long id);
}
