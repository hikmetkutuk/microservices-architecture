package com.microservices_architecture.customer.dto;

import com.microservices_architecture.customer.model.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
