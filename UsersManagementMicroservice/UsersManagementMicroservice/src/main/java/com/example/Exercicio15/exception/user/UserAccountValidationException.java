package com.example.Exercicio15.exception.user;

import org.springframework.http.HttpStatus;

import java.util.List;

import static com.example.Exercicio15.exception.ErrorMessage.USER_OPERATION_ERROR;

public class UserAccountValidationException extends Exception {
    private final List<String> errors;
    private final HttpStatus status;

    public UserAccountValidationException(List<String> errorMessages) {
        super(USER_OPERATION_ERROR.getMessage(errorMessages));
        this.errors = errorMessages;
        this.status = USER_OPERATION_ERROR.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}