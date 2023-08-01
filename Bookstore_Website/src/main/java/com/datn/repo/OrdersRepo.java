package com.datn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datn.entity.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

}
