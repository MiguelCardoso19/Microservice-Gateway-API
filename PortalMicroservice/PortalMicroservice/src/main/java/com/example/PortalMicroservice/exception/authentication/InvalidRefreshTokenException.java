package com.example.PortalMicroservice.exception.authentication;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.INVALID_REFRESH_TOKEN;

@Getter
public class InvalidRefreshTokenException extends Exception {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public InvalidRefreshTokenException() {
        super(INVALID_REFRESH_TOKEN.getMessage());
        this.message = INVALID_REFRESH_TOKEN.getMessage();
        this.code = INVALID_REFRESH_TOKEN.getCode();
        this.status = INVALID_REFRESH_TOKEN.getStatus();
    }
}