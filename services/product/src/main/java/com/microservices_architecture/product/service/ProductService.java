package com.microservices_architecture.product.service;

import com.microservices_architecture.product.dto.ProductPurchaseRequest;
import com.microservices_architecture.product.dto.ProductPurchaseResponse;
import com.microservices_architecture.product.dto.ProductRequest;
import com.microservices_architecture.product.dto.ProductResponse;
import com.microservices_architecture.product.exception.ProductCreationException;
import com.microservices_architecture.product.exception.ProductPurchaseException;
import com.microservices_architecture.product.mapper.ProductMapper;
import com.microservices_architecture.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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
                    product.getPrice(),
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription()
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

    public ProductResponse getProductById(int id) {
        try {
            log.info("Getting product with ID: {}", id);
            return productRepository.findById(id)
                    .map(mapper::fromProduct)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        } catch (Exception e) {
            log.error("Unexpected error occurred while getting product: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while getting product: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getQuantity() - productRequest.quantity();
            product.setQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
}
