package com.example.PortalMicroservice.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    INVALID_TOKEN("AUTH001", "Invalid token or username mismatch", HttpStatus.UNAUTHORIZED),
    USER_ID_NOT_FOUND("USR001", "User not found with ID: %s", HttpStatus.NOT_FOUND),
    USER_NAME_NOT_FOUND("USR002", "User not found with name: %s", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND("ADDR001", "Address not found with ID: %s", HttpStatus.NOT_FOUND),
    USER_OPERATION_ERROR("USR003", "User operation failed due to the following error/s: %s", HttpStatus.CONFLICT),
    ADDRESS_OPERATION_ERROR("ADDR002", "Address operation failed due to the following error/s: %s", HttpStatus.CONFLICT),
    INVALID_AUTHORIZATION_HEADER("AUTH002", "Authorization header missing or invalid", HttpStatus.UNAUTHORIZED),
    INVALID_PASSWORD("AUTH003", "This password is not valid", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("AUTH004", "This refresh token is not valid", HttpStatus.UNAUTHORIZED),
    GENERIC_MESSAGE("GEN001", "An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_CREDENTIALS_VALIDATION_ERROR("USR004", "User operation failed due to the following error/s: %s", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorMessage(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
