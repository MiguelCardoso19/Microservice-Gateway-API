package com.example.PortalMicroservice.exception.userManagement;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.PortalMicroservice.exception.ErrorMessage.ADDRESS_OPERATION_ERROR;

@Getter
public class AddressValidationException extends Exception {
    private final String errors;
    private final String code;
    private final HttpStatus status;

    public AddressValidationException(String errorMessage) {
        super(ADDRESS_OPERATION_ERROR.getMessage(errorMessage));
        this.errors = errorMessage;
        this.code = ADDRESS_OPERATION_ERROR.getCode();
        this.status = ADDRESS_OPERATION_ERROR.getStatus();
    }
}