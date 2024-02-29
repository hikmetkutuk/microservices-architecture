package com.microservices.inventoryservice.controller;

import com.microservices.inventoryservice.dto.InventoryRequest;
import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToStock(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.addToStock(inventoryRequest);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventoryList() {
        return inventoryService.getInventoryList();
    }

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
