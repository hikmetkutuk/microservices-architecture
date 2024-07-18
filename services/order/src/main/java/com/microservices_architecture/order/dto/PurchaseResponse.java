package com.microservices_architecture.order.dto;

import java.math.BigDecimal;

public record PurchaseResponse(
        int productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
