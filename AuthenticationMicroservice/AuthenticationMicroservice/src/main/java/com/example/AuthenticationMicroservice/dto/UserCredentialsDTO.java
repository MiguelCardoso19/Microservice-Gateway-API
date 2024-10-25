package com.example.AuthenticationMicroservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing user credentials, used for authentication and registration.")
public class UserCredentialsDTO extends AbstractDTO{

    @Schema(description = "Username of the user", example = "john_keen")
    private String username;

    @Email
    @Schema(description = "Email address of the user", example = "john.keen@example.com", required = true)
    private String email;

    @NotNull
    @NotEmpty
    @Schema(description = "Password of the user", example = "P@ssw0rd123", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "New password for updating credentials (if applicable)", example = "NewP@ssw0rd123")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;
}