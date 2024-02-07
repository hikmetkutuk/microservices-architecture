package com.microservices.inventoryservice.service;

import com.microservices.inventoryservice.dto.InventoryRequest;
import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.model.Inventory;
import com.microservices.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void addToStock(InventoryRequest inventoryRequest) {
        try {
            Inventory inventory = Inventory.builder()
                    .skuCode(inventoryRequest.skuCode())
                    .quantity(inventoryRequest.quantity())
                    .build();

            inventoryRepository.save(inventory);
            log.info("Successfully added {} quantity for SKU code {}", inventory.getQuantity(), inventory.getSkuCode());
        } catch (DataAccessException e) {
            log.error("Failed to add stock to inventory for SKU code {}: {}", inventoryRequest.skuCode(), e.getMessage());
            throw new RuntimeException("Failed to add stock to inventory for SKU code " + inventoryRequest.skuCode(), e);
        }
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public List<InventoryResponse> getInventoryList() {
        try {
            log.info("Fetching all inventory list from the database");
            return inventoryRepository.findAll()
                    .stream()
                    .map(this::mapToInventoryResponse)
                    .toList();
        } catch (DataAccessException e) {
            log.error("Failed to fetch all inventory list from the database: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch all inventory list from the database: " + e.getMessage());
        }
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }
}
