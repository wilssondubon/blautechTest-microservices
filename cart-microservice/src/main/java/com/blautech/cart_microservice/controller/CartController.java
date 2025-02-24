package com.blautech.cart_microservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.blautech.cart_microservice.dto.CartCreateDTO;
import com.blautech.cart_microservice.dto.CartResponseDTO;
import com.blautech.cart_microservice.dto.CartUpdateDTO;
import com.blautech.cart_microservice.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Integer id) {
        CartResponseDTO cart = cartService.getCartById(id);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getCartByUserId(@PathVariable Integer userId) {
        List<CartResponseDTO> carts = cartService.getCartByUserId(userId);
        if (carts == null || carts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/save")
    public ResponseEntity<CartResponseDTO> saveCart(@RequestBody @Validated CartCreateDTO cartDTO) {
        CartResponseDTO newCart = cartService.saveCart(cartDTO);
        return ResponseEntity.created(URI.create("/" + newCart.getId()))
            .body(newCart);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@PathVariable Integer id, @RequestBody @Validated CartUpdateDTO cartDTO) {
        CartResponseDTO updatedCart = cartService.updateCart(id, cartDTO);
        if (updatedCart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        boolean isDeleted = cartService.deleteCartById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteCartByUserId(@PathVariable Integer userId) {
        boolean isDeleted = cartService.deleteCartByUserId(userId);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}