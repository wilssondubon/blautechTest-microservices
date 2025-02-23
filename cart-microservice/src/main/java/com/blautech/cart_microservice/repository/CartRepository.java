package com.blautech.cart_microservice.repository;

import com.blautech.cart_microservice.entity.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public List<Cart> findByUserId(Integer userId);
}