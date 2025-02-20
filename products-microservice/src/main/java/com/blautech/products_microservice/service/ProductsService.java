package com.blautech.products_microservice.service;

import com.blautech.products_microservice.entity.Products;
import com.blautech.products_microservice.repository.ProductsRepository;
import com.blautech.products_microservice.dto.ProductsCreateDTO;
import com.blautech.products_microservice.dto.ProductsResponseDTO;
import com.blautech.products_microservice.dto.ProductsUpdateDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.TypeToken;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ProductsResponseDTO> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        return mapper.map(products, new TypeToken<List<ProductsResponseDTO>>() {}.getType());
    }

    public ProductsResponseDTO getById(int id) {
        Optional<Products> product = productsRepository.findById(id);
        if (!product.isPresent()) 
            return null;
        return product.map(p -> mapper.map(p, ProductsResponseDTO.class)).orElse(null);
    }

    public ProductsResponseDTO save(ProductsCreateDTO productsCreateDTO) {
        Products product = productsRepository.save(mapper.map(productsCreateDTO, Products.class));
        return mapper.map(product, ProductsResponseDTO.class);
    }

    public ProductsResponseDTO update(Integer id, ProductsUpdateDTO productUpdateDTO) {
        Optional<Products> productsOptional = productsRepository.findById(id);

		if (!productsOptional.isPresent()) {
			return null;
		}

		Products productsEntity = mapper.map(productUpdateDTO, Products.class);
		productsEntity.setId(id);

		Products productsUpdate = productsRepository.save(productsEntity);
		return mapper.map(productsUpdate, ProductsResponseDTO.class);
    }
}