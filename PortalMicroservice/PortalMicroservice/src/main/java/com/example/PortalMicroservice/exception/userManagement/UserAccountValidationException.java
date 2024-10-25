package com.example.PortalMicroservice.exception.userManagement;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.USER_OPERATION_ERROR;

@Getter
public class UserAccountValidationException extends Exception {
    private final String errors;
    private final String code;
    private final HttpStatus status;

    public UserAccountValidationException(String errorMessage) {
        super(USER_OPERATION_ERROR.getMessage(errorMessage));
        this.errors = errorMessage;
        this.code = USER_OPERATION_ERROR.getCode();
        this.status = USER_OPERATION_ERROR.getStatus();
    }
}