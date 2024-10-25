package com.example.Exercicio15.exception.address;

import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.example.Exercicio15.exception.ErrorMessage.ADDRESS_NOT_FOUND;

public class AddressNotFoundException extends Exception {
    private final String message;
    private final HttpStatus status;

    public AddressNotFoundException(UUID id) {
        super(ADDRESS_NOT_FOUND.getMessage(id));
        this.message = ADDRESS_NOT_FOUND.getMessage(id);
        this.status = ADDRESS_NOT_FOUND.getStatus();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}