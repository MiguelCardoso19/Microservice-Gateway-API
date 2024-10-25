package com.example.PortalMicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String Errorcode;
    private String message;
    private int status;

}
