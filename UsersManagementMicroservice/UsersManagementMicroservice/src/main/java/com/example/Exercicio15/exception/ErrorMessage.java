package com.example.Exercicio15.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorMessage {
    ADDRESS_NOT_FOUND("Address not found with ID: %s", NOT_FOUND),
    USER_ID_NOT_FOUND("User not found with ID: %s", NOT_FOUND),
    USER_NAME_NOT_FOUND("User not found with name: %s", NOT_FOUND),
    USER_OPERATION_ERROR("User operation failed due to the following error/s: %s", CONFLICT),
    ADDRESS_OPERATION_ERROR("Address operation failed due to the following error/s: %s", CONFLICT);


    private final String message;
    private final HttpStatus status;

    ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    public HttpStatus getStatus() {
        return status;
    }
}