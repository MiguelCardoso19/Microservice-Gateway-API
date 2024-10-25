package com.example.PortalMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.GENERIC_MESSAGE;

@Getter
public class GenericException extends Exception {
    private final String message;
    private final HttpStatus status;

    public GenericException() {
        super(GENERIC_MESSAGE.getMessage());
        this.message = GENERIC_MESSAGE.getMessage();
        this.status = GENERIC_MESSAGE.getStatus();
    }
}