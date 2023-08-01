package com.datn.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.datn.entity.Products;
import com.datn.repo.ProductsRepo;
import com.datn.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	@Autowired
	private ProductsRepo repo;

	@Override
	public List<Products> findAll() {
		return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0);
	}

	@Override
	public Products findById(Long id) {
		Optional<Products> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Products findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	// sau nay neu thuc hien update quantity trong admin 
	// ma bi bug thi nho bo value = TxType di
	@Transactional(value = TxType.REQUIRED)
	@Override
	public void updateQuantity(Integer newQuantity, Long id) {
		repo.updateQuantity(newQuantity, id);
	}

	@Override
	public Page<Products> findAll(int pageSize, int pageNumber) throws Exception {
		if (pageNumber >= 1) {
			return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0, PageRequest.of(pageNumber - 1, pageSize));
		}else {
			throw new Exception("Page number must be > 0");
		}

	}
}
