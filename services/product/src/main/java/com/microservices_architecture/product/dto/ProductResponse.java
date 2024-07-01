package com.microservices_architecture.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        int id,
        String name,
        String description,
        double quantity,
        BigDecimal price
) {
}
