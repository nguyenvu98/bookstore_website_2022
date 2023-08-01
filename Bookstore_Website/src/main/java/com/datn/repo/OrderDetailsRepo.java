package com.datn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datn.dto.CartDetailDto;
import com.datn.entity.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO order_details(orderId, productId, price, quantity)"
			+ " VALUES (:#{#dto.orderId}, :#{#dto.productId}, :#{#dto.price}, :#{#dto.quantity})", 
			nativeQuery = true)
	void insert(@Param("dto") CartDetailDto cartDetailDto);
}
