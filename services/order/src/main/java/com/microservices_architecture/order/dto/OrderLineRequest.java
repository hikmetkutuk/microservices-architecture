package com.microservices_architecture.order.dto;

public record OrderLineRequest(
        int orderId,
        int productId,
        double quantity
) {
}
