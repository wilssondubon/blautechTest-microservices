package com.blautech.orders_microservice.service;

import com.blautech.orders_microservice.config.RabbitMQConfig;
import com.blautech.orders_microservice.dto.CartResponseDTO;
import com.blautech.orders_microservice.dto.OrdersCreateDTO;
import com.blautech.orders_microservice.dto.OrdersDetailResponseDTO;
import com.blautech.orders_microservice.dto.OrdersResponseDTO;
import com.blautech.orders_microservice.dto.OrdersUpdateDTO;
import com.blautech.orders_microservice.dto.ProductsResponseDTO;
import com.blautech.orders_microservice.entity.Orders;
import com.blautech.orders_microservice.entity.OrdersDetail;
import com.blautech.orders_microservice.repository.OrdersDetailRepository;
import com.blautech.orders_microservice.repository.OrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blautech.orders_microservice.client.CartClient;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartClient cartClient;

    private final RabbitTemplate rabbitTemplate;

    public OrdersService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public OrdersResponseDTO getOrderById(Integer id) {
        Orders order = ordersRepository.findByIdWithOrderDetails(id);

        List<Integer> productIds = order.getOrderDetails().stream()
                .map(orderDetail -> orderDetail.getProductId())
                .distinct()
                .toList();

        List<ProductsResponseDTO> products = (List<ProductsResponseDTO>) rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMQConfig.REQUEST_QUEUE, productIds, new ParameterizedTypeReference<List<ProductsResponseDTO>>() {
                });

        return order != null ? new OrdersResponseDTO(order,
                order.getOrderDetails().stream().map(
                        orderDetail -> new OrdersDetailResponseDTO(orderDetail, products.stream()
                                .filter(p -> p.getId().equals(orderDetail.getProductId()))
                                .findFirst()
                                .orElse(null)))
                        .toList())
                : null;
    }

    public List<OrdersResponseDTO> getOrdersByUser(Integer userId) {
        List<Orders> orders = ordersRepository.findAllByUserIdWithOrderDetails(userId);

        List<Integer> productIds = orders.stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .map(orderDetail -> orderDetail.getProductId())
                .distinct()
                .toList();

        List<ProductsResponseDTO> products = (List<ProductsResponseDTO>) rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMQConfig.REQUEST_QUEUE, productIds, new ParameterizedTypeReference<List<ProductsResponseDTO>>() {
                });

        return orders.stream().map(
                order -> new OrdersResponseDTO(order,
                        order.getOrderDetails().stream().map(
                                orderDetail -> new OrdersDetailResponseDTO(orderDetail, products.stream()
                                        .filter(p -> p.getId().equals(orderDetail.getProductId()))
                                        .findFirst()
                                        .orElse(null)))
                                .toList()))
                .toList();

    }

   @Transactional
    public OrdersResponseDTO saveOrdersByCart(Integer userId){
        List<CartResponseDTO> cartItems = cartClient.getCartByUserId(userId);


        Orders newOrder = new Orders();
        newOrder.setUserId(userId);
        newOrder.setTotalPrice(cartItems.stream()
            .map(CartResponseDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        newOrder.setOrderDate( new Timestamp(new Date().getTime()) );
        //newOrder.setOrderDetails(ordersDetails);

        Orders savedOrder = ordersRepository.save(newOrder);

        List<OrdersDetail> ordersDetails = cartItems.stream()
            .map(cart -> {
                OrdersDetail orderDetail = new OrdersDetail();
                orderDetail.setProductId(cart.getProductId());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setSubtotal(cart.getSubtotal());
                orderDetail.setOrderId(savedOrder.getId());
                return orderDetail;
            })
            .collect(Collectors.toList());

        for (OrdersDetail detail: ordersDetails){
            ordersDetailRepository.save(detail);
        }

        //cartClient.deleteCartByUserId(userId);

        return mapper.map(savedOrder, OrdersResponseDTO.class);
    }

    public OrdersResponseDTO saveOrder(OrdersCreateDTO ordersCreateDTO) {
        Orders newOrder = mapper.map(ordersCreateDTO, Orders.class);
        Orders savedOrder = ordersRepository.save(newOrder);
        return mapper.map(savedOrder, OrdersResponseDTO.class);
    }

    public OrdersResponseDTO updateOrder(Integer id, OrdersUpdateDTO ordersUpdateDTO) {
        Optional<Orders> orderOptional = ordersRepository.findById(id);

        if (!orderOptional.isPresent()) {
            return null;
        }

        Orders orderEntity = mapper.map(ordersUpdateDTO, Orders.class);
        orderEntity.setId(id);

        Orders updatedOrder = ordersRepository.save(orderEntity);
        return mapper.map(updatedOrder, OrdersResponseDTO.class);
    }

    public boolean deleteOrderById(int id) {
        Optional<Orders> orderOptional = ordersRepository.findById(id);
        if (!orderOptional.isPresent()) {
            return false;
        }
        ordersRepository.deleteById(id);
        return true;
    }
}