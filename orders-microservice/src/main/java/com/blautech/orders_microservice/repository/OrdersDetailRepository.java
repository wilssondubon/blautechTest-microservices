package com.blautech.orders_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blautech.orders_microservice.entity.OrdersDetail;

@Repository
public interface OrdersDetailRepository  extends JpaRepository<OrdersDetail, Integer> {

}
