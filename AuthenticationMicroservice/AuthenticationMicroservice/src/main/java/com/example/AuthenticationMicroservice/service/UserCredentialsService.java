package com.example.AuthenticationMicroservice.service;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.InvalidPasswordException;
import com.example.AuthenticationMicroservice.exception.UserCredentialsValidationException;
import com.example.AuthenticationMicroservice.exception.UserNotFoundException;
import com.example.AuthenticationMicroservice.model.UserCredentials;

import java.util.Optional;

public interface UserCredentialsService {
    Optional<UserCredentials> findByUsername(String username);
    AuthenticationResponseDTO register(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsValidationException;
    void delete(UserCredentialsDTO userCredentialsDTO) throws InvalidPasswordException, UserNotFoundException;
    UserCredentialsDTO update(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsValidationException, InvalidPasswordException, UserNotFoundException;
}