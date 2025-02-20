package com.blautech.ordersdetail_microservice.controller;

import com.blautech.ordersdetail_microservice.service.OrdersDetailService;
import com.blautech.ordersdetail_microservice.dto.OrdersDetailCreateDTO;
import com.blautech.ordersdetail_microservice.dto.OrdersDetailResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ordersdetails")
public class OrdersDetailController {

    @Autowired
    OrdersDetailService ordersDetailService;

    @GetMapping("/order/{orderid}")
    public ResponseEntity<List<OrdersDetailResponseDTO>> getByOrderId(@PathVariable("orderid") int orderid) {
        List<OrdersDetailResponseDTO> ordersDetails = ordersDetailService.getByOrderId(orderid);
        if (ordersDetails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordersDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDetailResponseDTO> getById(@PathVariable("id") int id) {
        OrdersDetailResponseDTO ordersDetail = ordersDetailService.getById(id);
        if (ordersDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordersDetail);
    }

    @PostMapping("/save")
    public ResponseEntity<OrdersDetailResponseDTO> save(@RequestBody @Validated OrdersDetailCreateDTO ordersDetailDTO) {
        OrdersDetailResponseDTO ordersDetailNew = ordersDetailService.save(ordersDetailDTO);
        return ResponseEntity.created(URI.create("/" + ordersDetailNew.getId()))
                .body(ordersDetailNew);
    }
}