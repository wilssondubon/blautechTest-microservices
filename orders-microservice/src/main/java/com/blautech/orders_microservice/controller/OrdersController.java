package com.blautech.orders_microservice.controller;

import com.blautech.orders_microservice.dto.OrdersCreateDTO;
import com.blautech.orders_microservice.dto.OrdersResponseDTO;
import com.blautech.orders_microservice.dto.OrdersUpdateDTO;
import com.blautech.orders_microservice.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/{id}")
    public ResponseEntity<OrdersResponseDTO> getOrderById(@PathVariable Integer id) {
        OrdersResponseDTO order = ordersService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrdersResponseDTO>> getOrdersByUser(@PathVariable Integer userId) {
        List<OrdersResponseDTO> orders = ordersService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/save")
    public ResponseEntity<OrdersResponseDTO> saveOrder(@RequestBody @Validated OrdersCreateDTO ordersCreateDTO) {
        OrdersResponseDTO newOrder = ordersService.saveOrder(ordersCreateDTO);
        return ResponseEntity.created(URI.create("/" + newOrder.getId())).body(newOrder);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrdersResponseDTO> updateOrder(@PathVariable Integer id, @RequestBody @Validated OrdersUpdateDTO ordersUpdateDTO) {
        OrdersResponseDTO updatedOrder = ordersService.updateOrder(id, ordersUpdateDTO);
        if (updatedOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        boolean isDeleted = ordersService.deleteOrderById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}