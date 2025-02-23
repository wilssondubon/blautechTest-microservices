package com.blautech.cart_microservice.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.blautech.cart_microservice.config.RabbitMQConfig;
import com.blautech.cart_microservice.dto.CartCreateDTO;
import com.blautech.cart_microservice.dto.CartResponseDTO;
import com.blautech.cart_microservice.dto.CartUpdateDTO;
import com.blautech.cart_microservice.dto.ProductsResponseDTO;
import com.blautech.cart_microservice.entity.Cart;
import com.blautech.cart_microservice.repository.CartRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    private final RabbitTemplate rabbitTemplate;

    public CartService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public CartResponseDTO getCartById(Integer id) {
        Optional<Cart> cart = cartRepository.findById(id);

        if (!cart.isPresent())
            return null;

        List<Integer> productIds = Arrays.asList(cart.get().getProductId());

        List<ProductsResponseDTO> products = (List<ProductsResponseDTO>) rabbitTemplate.convertSendAndReceiveAsType(RabbitMQConfig.REQUEST_QUEUE, productIds, new ParameterizedTypeReference<List<ProductsResponseDTO>>() {});

        CartResponseDTO cartResponse = cart.map(value -> mapper.map(value, CartResponseDTO.class)).orElse(null);

        if (cartResponse == null)
            return null;

        cartResponse.setProduct(products.stream().filter(p->p.getId().equals(cartResponse.getProductId())).findFirst().get());

        return cart.map(value -> mapper.map(value, CartResponseDTO.class)).orElse(null);
    }

    public List<CartResponseDTO> getCartByUserId(Integer userId){
        List<Cart> carts = cartRepository.findByUserId(userId);

        List<Integer> productIds = carts.stream()
            .map(Cart::getProductId)
            .toList();

        List<ProductsResponseDTO> products = (List<ProductsResponseDTO>) rabbitTemplate.convertSendAndReceiveAsType(RabbitMQConfig.REQUEST_QUEUE, productIds, new ParameterizedTypeReference<List<ProductsResponseDTO>>() {});

        return carts.stream().map(cartItem -> new CartResponseDTO(cartItem, products.stream().filter(p->p.getId().equals(cartItem.getProductId())).findFirst().orElse(null))).toList();
    }

    public CartResponseDTO saveCart(CartCreateDTO cartDTO) {
        Cart newCart = mapper.map(cartDTO, Cart.class);
        Cart savedCart = cartRepository.save(newCart);
        return mapper.map(savedCart, CartResponseDTO.class);
    }

    public CartResponseDTO updateCart(Integer id, CartUpdateDTO cartDTO) {
        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (!cartOptional.isPresent()) {
            return null;
        }

        Cart cartEntity = mapper.map(cartDTO, Cart.class);
        cartEntity.setId(id);

        Cart updatedCart = cartRepository.save(cartEntity);
        return mapper.map(updatedCart, CartResponseDTO.class);
    }

    public boolean deleteCartById(Integer id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (!cartOptional.isPresent()) {
            return false;
        }
        cartRepository.deleteById(id);
        return true;
    }
}