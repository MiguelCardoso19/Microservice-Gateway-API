package com.example.AuthenticationMicroservice.controller;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.*;
import com.example.AuthenticationMicroservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Handles user authentication, token validation, and refresh token operations.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Sign in with user credentials",
            description = "Authenticate user and return JWT token if the credentials are valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, returns JWT token"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponseDTO> signIn(
            @Valid @RequestBody @Parameter(description = "User credentials (username and password)") UserCredentialsDTO userCredentialsDTO
    ) throws InvalidPasswordException, UserNotFoundException {
        return ResponseEntity.ok(authenticationService.signIn(userCredentialsDTO));
    }

    @Operation(summary = "Refresh the JWT token",
            description = "Refresh the user's JWT token using the refresh token.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            @Parameter(description = "HTTP request to extract refresh token") HttpServletRequest request
    ) throws IOException, InvalidRefreshTokenException, UserNotFoundException {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}