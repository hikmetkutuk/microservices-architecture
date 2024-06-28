package com.microservices_architecture.customer.exception;

public class CustomerDeletionException extends RuntimeException {
    public CustomerDeletionException(String message) {
        super(message);
    }

    public CustomerDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
