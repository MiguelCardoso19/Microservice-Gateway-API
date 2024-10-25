package com.example.AuthenticationMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.AuthenticationMicroservice.exception.ErrorMessage.INVALID_REFRESH_TOKEN;

@Getter
public class InvalidRefreshTokenException extends Exception {
    private final String message;
    private final HttpStatus status;

    public InvalidRefreshTokenException() {
        super(INVALID_REFRESH_TOKEN.getMessage());
        this.message = INVALID_REFRESH_TOKEN.getMessage();
        this.status = INVALID_REFRESH_TOKEN.getStatus();
    }
}