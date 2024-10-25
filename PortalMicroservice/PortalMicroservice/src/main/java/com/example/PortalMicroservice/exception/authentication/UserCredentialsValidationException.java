package com.example.PortalMicroservice.exception.authentication;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.USER_CREDENTIALS_VALIDATION_ERROR;

@Getter
public class UserCredentialsValidationException extends Exception {
    private final String errors;
    private final String code;
    private final HttpStatus status;

    public UserCredentialsValidationException(String errorMessage) {
        super(USER_CREDENTIALS_VALIDATION_ERROR.getMessage(errorMessage));
        this.errors = errorMessage;
        this.code = USER_CREDENTIALS_VALIDATION_ERROR.getCode();
        this.status = USER_CREDENTIALS_VALIDATION_ERROR.getStatus();
    }
}