package com.microservices_architecture.product.service;

import com.microservices_architecture.product.dto.ProductRequest;
import com.microservices_architecture.product.dto.ProductResponse;
import com.microservices_architecture.product.exception.ProductCreationException;
import com.microservices_architecture.product.mapper.ProductMapper;
import com.microservices_architecture.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        try {
            var product = productRepository.save(mapper.toProduct(productRequest));
            log.info("Product created successfully with ID: {}", product.getId());
            return new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getQuantity(),
                    product.getPrice()
            );
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while creating product: " + e.getMessage());
            throw new ProductCreationException("Data integrity violation occurred while creating product: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating product: " + e.getMessage());
            throw new ProductCreationException("Unexpected error occurred while creating product: " + e.getMessage());
        }
    }

    public List<ProductResponse> getAllProducts() {
        try {
            log.info("Getting all products");
            return productRepository.findAll()
                    .stream()
                    .map(mapper::fromProduct)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Unexpected error occurred while getting all products: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while getting all products: " + e.getMessage());
        }
    }
}
