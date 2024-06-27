package com.microservices_architecture.customer.service;

import com.microservices_architecture.customer.dto.CustomerRequest;
import com.microservices_architecture.customer.dto.CustomerResponse;
import com.microservices_architecture.customer.exception.CustomerCreationException;
import com.microservices_architecture.customer.exception.CustomerNotFoundException;
import com.microservices_architecture.customer.exception.CustomerRetrievalException;
import com.microservices_architecture.customer.mapper.CustomerMapper;
import com.microservices_architecture.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        try {
            var customer = customerRepository.save(mapper.toCustomer(customerRequest));
            log.info("Customer created successfully with email: {}", customer.getEmail());
            return new CustomerResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getAddress()
            );
        } catch (DuplicateKeyException e) {
            log.error("Email address already exists");
            throw new CustomerCreationException("Email address already exists: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while creating customer: " + e.getMessage());
            throw new CustomerCreationException("Data integrity violation occurred while creating customer: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating customer: " + e.getMessage());
            throw new CustomerCreationException("Unexpected error occurred while creating customer: " + e.getMessage());
        }
    }

    public List<CustomerResponse> getAllCustomers() {
        try {
            log.info("Getting all customers");
            return customerRepository.findAll()
                    .stream()
                    .map(mapper::fromCustomer)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Unexpected error occurred while getting all customers: " + e.getMessage());
            throw new CustomerRetrievalException("Unexpected error occurred while getting all customers: " + e.getMessage());
        }
    }

    public CustomerResponse getCustomerById(String id) {
        try {
            log.info("Getting customer with id: {}", id);
            return customerRepository.findById(id)
                    .map(mapper::fromCustomer)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        } catch (Exception e) {
            log.error("Unexpected error occurred while getting customer: " + e.getMessage());
            throw new CustomerRetrievalException("Unexpected error occurred while getting customer: " + e.getMessage());
        }
    }
}
