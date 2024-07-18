package com.microservices_architecture.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
    @NotNull(message = "Product is mandatory")
    int productId,
    @Positive(message = "Quantity must be positive")
    int quantity
) {
}
