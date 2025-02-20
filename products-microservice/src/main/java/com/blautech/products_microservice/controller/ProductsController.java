package com.blautech.products_microservice.controller;

import com.blautech.products_microservice.service.ProductsService;
import com.blautech.products_microservice.dto.ProductsCreateDTO;
import com.blautech.products_microservice.dto.ProductsResponseDTO;
import com.blautech.products_microservice.dto.ProductsUpdateDTO;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductsResponseDTO>> getAll() {
        List<ProductsResponseDTO> products = productsService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> getProductById(@PathVariable("id") int id) {
        ProductsResponseDTO product = productsService.getById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductsResponseDTO> save(@RequestBody @Validated ProductsCreateDTO product) {
        ProductsResponseDTO productNew = productsService.save(product);
        return ResponseEntity.created(URI.create("/" + productNew.getId()))
                .body(productNew);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductsResponseDTO> update(@PathVariable Integer id, @RequestBody @Validated ProductsUpdateDTO product) {
        ProductsResponseDTO productUpdate = productsService.update(id, product);
        if (productUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdate);
    }
}