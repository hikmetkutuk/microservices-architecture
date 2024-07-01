package com.microservices_architecture.product.controller;

import com.microservices_architecture.product.dto.ProductRequest;
import com.microservices_architecture.product.dto.ProductResponse;
import com.microservices_architecture.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product based on the provided product request.
     *
     * @param  productRequest   the product request containing details of the product
     * @return                  the response entity with the product response
     */
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }
}
