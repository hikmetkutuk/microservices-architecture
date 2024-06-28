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

    /**
     * A description of the entire Java function.
     *
     * @param id description of parameter
     * @return description of return value
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Update a customer based on the provided ID and request.
     *
     * @param id              the ID of the customer to update
     * @param customerRequest the updated customer details
     * @return response entity with updated customer details
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return the response entity with the result of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
