package com.microservices_architecture.product.dto;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    String description,
    double quantity,
    BigDecimal price
) {
}
