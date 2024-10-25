package com.example.AuthenticationMicroservice.service;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDTO signIn(UserCredentialsDTO userCredentialsDTO) throws InvalidPasswordException, UserNotFoundException;
    AuthenticationResponseDTO refreshToken(HttpServletRequest request) throws InvalidRefreshTokenException, IOException, UserNotFoundException;
}