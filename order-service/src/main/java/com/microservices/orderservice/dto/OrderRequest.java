package com.microservices.orderservice.dto;

import java.util.List;

public record OrderRequest(
        List<OrderLineItemsResponse> orderLineItemsList
) {
}
