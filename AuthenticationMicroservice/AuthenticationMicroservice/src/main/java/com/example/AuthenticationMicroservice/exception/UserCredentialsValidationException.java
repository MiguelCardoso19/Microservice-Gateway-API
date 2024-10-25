package com.example.AuthenticationMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.example.AuthenticationMicroservice.exception.ErrorMessage.USER_CREDENTIALS_VALIDATION_ERROR;

@Getter
public class UserCredentialsValidationException extends Exception {
    private final List<String> errors;
    private final HttpStatus status;

    public UserCredentialsValidationException(List<String> errorMessages) {
        super(USER_CREDENTIALS_VALIDATION_ERROR.getMessage(errorMessages));
        this.errors = errorMessages;
        this.status = USER_CREDENTIALS_VALIDATION_ERROR.getStatus();
    }
}