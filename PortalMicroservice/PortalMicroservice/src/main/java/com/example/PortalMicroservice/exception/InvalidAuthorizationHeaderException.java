package com.example.PortalMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.INVALID_AUTHORIZATION_HEADER;

@Getter
public class InvalidAuthorizationHeaderException extends Exception {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public InvalidAuthorizationHeaderException() {
        super(INVALID_AUTHORIZATION_HEADER.getMessage());
        this.message = INVALID_AUTHORIZATION_HEADER.getMessage();
        this.code = INVALID_AUTHORIZATION_HEADER.getCode();
        this.status = INVALID_AUTHORIZATION_HEADER.getStatus();
    }
}