package com.blautech.orders_microservice.repository;

import com.blautech.orders_microservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderDetails WHERE o.id = :id")
    Orders findByIdWithOrderDetails(@Param("id") Integer id);

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderDetails")
    List<Orders> findAllWithOrderDetails();

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.orderDetails WHERE o.userId = :userId")
    List<Orders> findAllByUserIdWithOrderDetails(@Param("userId") Integer userId);
}