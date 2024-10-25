package com.example.PortalMicroservice.config;

import com.example.PortalMicroservice.exception.ErrorResponse;
import com.example.PortalMicroservice.exception.InvalidAuthorizationHeaderException;
import com.example.PortalMicroservice.exception.InvalidTokenException;
import com.example.PortalMicroservice.exception.authentication.*;
import com.example.PortalMicroservice.exception.userManagement.AddressNotFoundException;
import com.example.PortalMicroservice.exception.userManagement.AddressValidationException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.exception.userManagement.UserAccountValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthorizationHeaderException(InvalidAuthorizationHeaderException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserAccountValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountValidationException(UserAccountValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(AddressValidationException.class)
    public ResponseEntity<ErrorResponse> handleAddressValidationException(AddressValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
    @ExceptionHandler(UserCredentialsValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserCredentialsValidationException(UserCredentialsValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}