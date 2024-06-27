package com.microservices_architecture.customer.controller;

import com.microservices_architecture.customer.dto.CustomerRequest;
import com.microservices_architecture.customer.dto.CustomerResponse;
import com.microservices_architecture.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Create a new customer based on the provided customer request.
     *
     * @param customerRequest request object containing customer details
     * @return response entity with the created customer details
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    /**
     * Get all customers.
     *
     * @return list of CustomerResponse objects
     */
    @GetMapping("/list")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
