package com.example.PortalMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.example.PortalMicroservice.exception.ErrorMessage.*;

@Getter
public class UserNotFoundException extends Exception {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public UserNotFoundException(UUID id) {
        super(USER_ID_NOT_FOUND.getMessage(id));
        this.message = USER_ID_NOT_FOUND.getMessage(id);
        this.code = USER_ID_NOT_FOUND.getCode();
        this.status = USER_ID_NOT_FOUND.getStatus();
    }

    public UserNotFoundException(String name) {
        super(USER_NAME_NOT_FOUND.getMessage(name));
        this.message = USER_NAME_NOT_FOUND.getMessage(name);
        this.code = USER_NAME_NOT_FOUND.getCode();
        this.status = USER_NAME_NOT_FOUND.getStatus();
    }
}