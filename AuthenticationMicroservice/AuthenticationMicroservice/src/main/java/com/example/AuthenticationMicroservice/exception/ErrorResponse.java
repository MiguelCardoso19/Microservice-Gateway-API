package com.example.AuthenticationMicroservice.exception;

import java.util.List;

public class ErrorResponse {
    private final String message;
    private final int status;
    private List<String> errors;

    public ErrorResponse(String message, List<String> errors, int status) {
        this.errors = errors;
        this.message = message;
        this.status = status;
    }

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}