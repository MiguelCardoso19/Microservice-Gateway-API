package com.example.Exercicio15.dto;

import com.example.Exercicio15.enumerator.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Schema(description = "Data Transfer Object representing a User Account")
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}