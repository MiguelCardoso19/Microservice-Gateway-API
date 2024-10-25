package com.example.AuthenticationMicroservice.service.impl;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.*;
import com.example.AuthenticationMicroservice.mapper.DTOMapper;
import com.example.AuthenticationMicroservice.model.UserCredentials;
import com.example.AuthenticationMicroservice.service.AuthenticationService;
import com.example.AuthenticationMicroservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DTOMapper dtoMapper;
    private final UserCredentialsServiceImpl userCredentialsService;
    private final ObjectMapper objectMapper;


    @Override
    public AuthenticationResponseDTO signIn(UserCredentialsDTO userCredentialsDTO) throws InvalidPasswordException, UserNotFoundException {
        UserCredentials user = userCredentialsService.findByUsername(userCredentialsDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userCredentialsDTO.getUsername()));

        if (!passwordEncoder.matches(userCredentialsDTO.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return dtoMapper.toDTO(jwtService.generateToken(user), jwtService.generateRefreshToken(user), user.getId());
    }

    @Override
    public AuthenticationResponseDTO refreshToken(HttpServletRequest request) throws UserNotFoundException {
        String authHeader = request.getHeader("Authorization");
        String refreshToken = authHeader.substring(7).trim();
        String username = jwtService.extractUsername(refreshToken);
        AuthenticationResponseDTO authenticationResponseDTO = null;

        UserCredentials user = userCredentialsService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (jwtService.isTokenValid(refreshToken, username)) {

            authenticationResponseDTO = dtoMapper.toDTOWithoutUserID(
                    jwtService.generateToken(user), refreshToken);

        }

        return authenticationResponseDTO;
    }
}