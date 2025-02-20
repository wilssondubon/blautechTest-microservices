package com.blautech.ordersdetail_microservice.repository;

import com.blautech.ordersdetail_microservice.entity.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {

    public List<OrdersDetail> findByOrderId(Integer orderId);
}
