package com.blautech.products_microservice.repository;

import com.blautech.products_microservice.entity.Products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    List<Products> findAllById(Iterable<Integer> productIds);
}