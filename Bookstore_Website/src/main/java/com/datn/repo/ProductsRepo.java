package com.datn.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datn.entity.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
	
	List<Products> findByProductType_Id(Long typeId);
	Products findBySlug(String slug);
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity);
	

	Page<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable pageable);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET quantity = ?1 WHERE id = ?2", nativeQuery = true)
	void updateQuantity(Integer newQuantity, Long id);
}
