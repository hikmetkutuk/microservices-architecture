package com.microservices.inventoryservice.dto;

public record InventoryResponse(
        Long id,
        String skuCode,
        Integer quantity
) {
}
