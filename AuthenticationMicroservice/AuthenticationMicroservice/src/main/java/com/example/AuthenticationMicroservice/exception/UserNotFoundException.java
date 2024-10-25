package com.example.AuthenticationMicroservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.example.AuthenticationMicroservice.exception.ErrorMessage.USERNAME_NOT_FOUND;
import static com.example.AuthenticationMicroservice.exception.ErrorMessage.USER_ID_NOT_FOUND;

@Getter
public class UserNotFoundException extends Exception {
    private final String message;
    private final HttpStatus status;

    public UserNotFoundException(UUID id) {
        super(USER_ID_NOT_FOUND.getMessage(id));
        this.message = USER_ID_NOT_FOUND.getMessage(id);
        this.status = USER_ID_NOT_FOUND.getStatus();
    }

    public UserNotFoundException(String username) {
        super(USERNAME_NOT_FOUND.getMessage(username));
        this.message = USERNAME_NOT_FOUND.getMessage(username);
        this.status = USERNAME_NOT_FOUND.getStatus();
    }
}