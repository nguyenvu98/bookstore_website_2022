package com.datn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datn.entity.ProductTypes;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductTypes, Long>{

}
