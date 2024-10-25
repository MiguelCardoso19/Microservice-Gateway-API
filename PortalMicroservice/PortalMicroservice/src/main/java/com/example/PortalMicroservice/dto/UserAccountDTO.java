package com.example.PortalMicroservice.dto;

import com.example.PortalMicroservice.enumerator.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Schema(description = "Data Transfer Object representing a User Account")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class UserAccountDTO {

    @Schema(description = "Unique identifier for the user account", example = "b3a3f1e3-3e3b-4c5d-8c11-e51e4d3b3c21")
    private UUID id;

    @NotNull(message = "Full name is required")
    @NotEmpty(message = "Full name is required")
    @Schema(description = "Full name of the user", required = true, example = "Miguel Trump Bush Biden")
    private String name;

    @NotNull(message = "Birthdate is required")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Schema(description = "Birthdate of the user in yyyy/MM/dd format", required = true, example = "1990/01/12")
    private Date birthdate;

    @Min(value = 100000000, message = "Phone number must be at least 9 digits")
    @Schema(description = "User's phone number, must be at least 9 digits", required = true, example = "913001300")
    private int phoneNumber;

    @NotNull(message = "Email is required")
    @Email(message = "Email is required or invalid")
    @Schema(description = "Email address of the user", required = true, example = "johndoe@example.com")
    private String email;

    @Min(value = 100000000, message = "NIF must be at least 9 digits")
    @Schema(description = "Tax Identification Number of the user, must be at least 9 digits", example = "123456789")
    private int nif;

    @Schema(description = "Role assigned to the user in the system", example = "USER")
    private UserRole role;

    @Schema(description = "List of addresses associated with the user")
    private List<AddressDTO> addresses;
}