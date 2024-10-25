package com.example.Exercicio15.exception.user;

import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.example.Exercicio15.exception.ErrorMessage.*;

public class UserAccountNotFoundException extends Exception {
    private final String message;
    private final HttpStatus status;

    public UserAccountNotFoundException(UUID id) {
        super(USER_ID_NOT_FOUND.getMessage(id));
        this.message = USER_ID_NOT_FOUND.getMessage(id);
        this.status = USER_ID_NOT_FOUND.getStatus();
    }

    public UserAccountNotFoundException(String name) {
        super(USER_NAME_NOT_FOUND.getMessage(name));
        this.message = USER_NAME_NOT_FOUND.getMessage(name);
        this.status = USER_NAME_NOT_FOUND.getStatus();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}