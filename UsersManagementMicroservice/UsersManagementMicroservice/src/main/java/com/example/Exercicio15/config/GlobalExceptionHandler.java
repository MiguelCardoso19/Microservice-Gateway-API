package com.example.Exercicio15.config;

import com.example.Exercicio15.exception.*;
import com.example.Exercicio15.exception.address.AddressNotFoundException;
import com.example.Exercicio15.exception.address.AddressValidationException;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.exception.user.UserAccountValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAccountValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountValidationException(UserAccountValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrors(),
                ex.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(AddressValidationException.class)
    public ResponseEntity<ErrorResponse> handleAddressValidationException(AddressValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrors(),
                ex.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserAccountNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}