package com.microservices_architecture.customer.dto;

import com.microservices_architecture.customer.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotNull(message = "customer firstName cannot be null")
        String firstName,
        @NotNull(message = "customer lastName cannot be null")
        String lastName,
        @Email(message = "customer email must be valid")
        @NotNull(message = "customer email cannot be null")
        String email,
        Address address
) {
}
