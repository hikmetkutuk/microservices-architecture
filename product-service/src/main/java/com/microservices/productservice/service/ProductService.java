package com.microservices.productservice.service;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        try {
            productRepository.save(product);
            log.info("Product {} is saved", product.getId());
        } catch (DataAccessException e) {
            log.error("Failed to save product to the database: {}", e.getMessage());
            throw new RuntimeException("Failed to save product to the database: " + e.getMessage());
        }
    }

    public List<ProductResponse> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            log.info("Fetching all products from the database");
            return products.stream().map(this::mapToProductResponse).toList();
        } catch (DataAccessException e) {
            log.error("Failed to fetch all products from the database: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch all products from the database: " + e.getMessage());
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
