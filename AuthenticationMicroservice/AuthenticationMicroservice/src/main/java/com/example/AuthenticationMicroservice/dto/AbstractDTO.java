package com.example.AuthenticationMicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AbstractDTO {
    @Schema(description = "Unique identifier for the user", example = "e12a567d-32f8-4e9a-9073-6781f9d5e423")
    private UUID id;
}
