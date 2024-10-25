package com.example.PortalMicroservice.controller.authentication;

import com.example.PortalMicroservice.client.AuthenticationFeignClient;
import com.example.PortalMicroservice.dto.AuthenticationResponseDTO;
import com.example.PortalMicroservice.dto.UserCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/proxy/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "API for authentication, communicating with the Authentication microservice.")
public class AuthenticationController {
    private final AuthenticationFeignClient authenticationFeignClient;

    @Operation(
            summary = "Sign in using user credentials",
            description = "Authenticate a user through the proxy and return a JWT token if successful."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, returns JWT token"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials"),
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponseDTO> signIn(
            @Valid @RequestBody @Parameter(description = "User credentials including username and password") UserCredentialsDTO userCredentialsDTO
    ) {
        return authenticationFeignClient.signIn(userCredentialsDTO);
    }

    @Operation(
            summary = "Refresh JWT token",
            description = "Refresh the JWT token using the Authorization header through the proxy.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(
            @Parameter(description = "Authorization header containing the Bearer token") HttpServletRequest request
    ) {
        return authenticationFeignClient.refreshToken(request.getHeader("Authorization"));
    }
}
