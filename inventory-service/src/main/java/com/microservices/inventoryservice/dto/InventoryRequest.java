package com.microservices.inventoryservice.dto;

public record InventoryRequest(
        String skuCode,
        Integer quantity
) {
}
