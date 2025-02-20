package com.blautech.ordersdetail_microservice.service;

import com.blautech.ordersdetail_microservice.entity.OrdersDetail;
import com.blautech.ordersdetail_microservice.repository.OrdersDetailRepository;
import com.blautech.ordersdetail_microservice.dto.OrdersDetailCreateDTO;
import com.blautech.ordersdetail_microservice.dto.OrdersDetailResponseDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import org.modelmapper.TypeToken;


@Service
public class OrdersDetailService {

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Autowired
    private ModelMapper mapper;


    public List<OrdersDetailResponseDTO> getByOrderId(Integer orderId) {
        List<OrdersDetail> ordersDetails = ordersDetailRepository.findByOrderId(orderId);
        return mapper.map(ordersDetails, new TypeToken<List<OrdersDetailResponseDTO>>() {}.getType());
    }


    public OrdersDetailResponseDTO getById(int id) {
        Optional<OrdersDetail> ordersDetail = ordersDetailRepository.findById(id);
        if (!ordersDetail.isPresent()) 
            return null;

        return ordersDetail.map(p -> mapper.map(p, OrdersDetailResponseDTO.class)).orElse(null);
    }

    public OrdersDetailResponseDTO save(OrdersDetailCreateDTO ordersDetailDTO) {
        OrdersDetail ordersDetail = ordersDetailRepository.save(mapper.map(ordersDetailDTO, OrdersDetail.class));
        return  mapper.map(ordersDetail, OrdersDetailResponseDTO.class);
    }

}