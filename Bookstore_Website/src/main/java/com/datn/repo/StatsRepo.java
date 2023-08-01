package com.datn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datn.entity.Orders;

@Repository
public interface StatsRepo extends JpaRepository<Orders, Long> {
	
	@Query(value = "{CALL sp_getToTalPriceMonth(:month, :year)}", nativeQuery = true)
	String getTotalPricePerMonth(@Param("month") String month, @Param("year") String year);
 }
