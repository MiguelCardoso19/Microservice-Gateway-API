package com.example.PortalMicroservice.exception.userManagement;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.example.PortalMicroservice.exception.ErrorMessage.ADDRESS_NOT_FOUND;

@Getter
public class AddressNotFoundException extends Exception {
    private final String message;
    private final String code;
    private final HttpStatus status;

    public AddressNotFoundException(UUID id) {
        super(ADDRESS_NOT_FOUND.getMessage(id));
        this.message = ADDRESS_NOT_FOUND.getMessage(id);
        this.code = ADDRESS_NOT_FOUND.getCode();
        this.status = ADDRESS_NOT_FOUND.getStatus();
    }
}