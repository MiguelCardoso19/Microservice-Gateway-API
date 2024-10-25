package com.example.PortalMicroservice.exception.authentication;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.PortalMicroservice.exception.ErrorMessage;

@Getter
public class InvalidPasswordException extends Exception {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public InvalidPasswordException() {
        super(ErrorMessage.INVALID_PASSWORD.getMessage());
        this.message = ErrorMessage.INVALID_PASSWORD.getMessage();
        this.code = ErrorMessage.INVALID_PASSWORD.getCode();
        this.status = ErrorMessage.INVALID_PASSWORD.getStatus();
    }
}