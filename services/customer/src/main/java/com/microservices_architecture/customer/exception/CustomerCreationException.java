package com.microservices_architecture.customer.exception;

public class CustomerCreationException extends RuntimeException {
    public CustomerCreationException(String message) {
        super(message);
    }

    public CustomerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}