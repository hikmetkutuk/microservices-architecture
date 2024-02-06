package com.microservices.orderservice.dto;

import java.math.BigDecimal;

public record OrderLineItemsResponse(
        Long id,
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
