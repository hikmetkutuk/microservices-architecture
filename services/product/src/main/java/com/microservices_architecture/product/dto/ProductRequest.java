package com.microservices_architecture.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Product name cannot be null")
        String name,
        @NotNull(message = "Product description field cannot be null")
        String description,
        @Positive(message = "Product quantity must be greater than zero")
        double quantity,
        @Positive(message = "Product price must be greater than zero")
        BigDecimal price,
        @NotNull(message = "Product category cannot be null")
        Integer categoryId
) {
}
