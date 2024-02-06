package com.microservices.productservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be null or empty.")
        String name,
        String description,
        @NotNull(message = "Product price cannot be null.")
        @DecimalMin(value = "0.01", message = "Product price must be greater than or equal to 0.01")
        BigDecimal price
) {
}
