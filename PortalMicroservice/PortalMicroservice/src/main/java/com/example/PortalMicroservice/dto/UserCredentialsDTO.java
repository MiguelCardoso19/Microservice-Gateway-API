package com.example.PortalMicroservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing user credentials, used for authentication and registration.")
public class UserCredentialsDTO {

    @Schema(description = "Unique identifier for the user", example = "e12a567d-32f8-4e9a-9073-6781f9d5e423")
    private UUID id;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Email
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;

    @NotNull
    @NotEmpty
    @Schema(description = "Password of the user", example = "P@ssw0rd123", required = true)
    private String password;

    @Schema(description = "New password for updating credentials (if applicable)", example = "NewP@ssw0rd123")
    private String newPassword;
}