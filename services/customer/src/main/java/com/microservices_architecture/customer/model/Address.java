package com.microservices_architecture.customer.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Builder
@Document
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;
}
