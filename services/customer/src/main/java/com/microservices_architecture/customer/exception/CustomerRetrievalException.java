package com.microservices_architecture.customer.exception;

public class CustomerRetrievalException extends RuntimeException {
    public CustomerRetrievalException(String message) {
        super(message);
    }

    public CustomerRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
