package com.example.PortalMicroservice.dto;

import com.example.PortalMicroservice.enumerator.CountryEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Schema(description = "Data Transfer Object representing an Address")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AddressDTO {

    @Schema(description = "Unique identifier for the address", example = "b3a3f1e3-3e3b-4c5d-8c11-e51e4d3b3c21")
    private UUID id;

    @Schema(description = "Country of the address", required = true, example = "USA")
    @NotNull(message = "Country is required")
    private CountryEnum country;

    @Schema(description = "Street name and number", required = true, example = "123 Main St")
    @NotBlank(message = "Street is required")
    @Size(max = 20, message = "Street cannot exceed 255 characters")
    private String street;

    @Schema(description = "City of the address", required = true, example = "New York")
    @NotBlank(message = "City is required")
    @Size(max = 15, message = "City cannot exceed 100 characters")
    private String city;

    @Schema(description = "State of the address", required = true, example = "NY")
    @NotBlank(message = "State is required")
    @Size(max = 15, message = "State cannot exceed 100 characters")
    private String state;

    @Schema(description = "Postal code of the address", required = true, example = "10001")
    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @Schema(description = "User account associated with this address")
    private UserAccountDTO user;
}