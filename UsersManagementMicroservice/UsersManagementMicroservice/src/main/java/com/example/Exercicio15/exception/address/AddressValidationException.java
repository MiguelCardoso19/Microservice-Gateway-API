package com.example.Exercicio15.exception.address;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.example.Exercicio15.exception.ErrorMessage.ADDRESS_OPERATION_ERROR;

public class AddressValidationException extends Exception {
    private final List<String> errors;
    private final HttpStatus status;

    public AddressValidationException(List<String> errorMessages) {
        super(ADDRESS_OPERATION_ERROR.getMessage(errorMessages));
        this.errors = errorMessages;
        this.status = ADDRESS_OPERATION_ERROR.getStatus();
    }

    public List<String> getErrors() {
        return errors;
    }

    public HttpStatus getStatus() {
        return status;
    }
}