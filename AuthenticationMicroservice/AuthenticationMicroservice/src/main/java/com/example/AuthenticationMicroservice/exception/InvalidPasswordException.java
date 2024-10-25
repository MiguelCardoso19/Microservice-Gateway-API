package com.example.AuthenticationMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.AuthenticationMicroservice.exception.ErrorMessage.INVALID_PASSWORD;

@Getter
public class InvalidPasswordException  extends Exception {
    private final String message;
    private final HttpStatus status;

    public InvalidPasswordException() {
        super(INVALID_PASSWORD.getMessage());
        this.message = INVALID_PASSWORD.getMessage();
        this.status = INVALID_PASSWORD.getStatus();
    }
}