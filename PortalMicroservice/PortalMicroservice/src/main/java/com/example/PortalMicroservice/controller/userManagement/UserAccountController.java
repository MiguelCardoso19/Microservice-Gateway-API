package com.example.PortalMicroservice.controller.userManagement;

import com.example.PortalMicroservice.dto.UserAccountDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.exception.userManagement.UserAccountValidationException;
import com.example.PortalMicroservice.service.impl.UserAccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/proxy/users")
@RequiredArgsConstructor
@Tag(name = "User Account Management API", description = "API for managing user accounts including creation, retrieval, updating, and deletion.")
public class UserAccountController {
    private final UserAccountServiceImpl userAccountServiceImpl;

    @Operation(summary = "Create a new user account",
            description = "Creates a new user account and returns the created user account data.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user account data provided"),
    })
    @PostMapping("/create")
    public ResponseEntity<UserAccountDTO> createUser(
            @Parameter(description = "User account details to create") @RequestBody UserAccountDTO userAccountDTO
    ) throws UserAccountValidationException, GenericException {
        return ResponseEntity.ok(userAccountServiceImpl.createUser(userAccountDTO));
    }

    @Operation(summary = "Get user account by ID",
            description = "Retrieves the user account details by the specified ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User account not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> getUserById(
            @Parameter(description = "ID of the user account to retrieve") @PathVariable UUID id
    ) throws UserNotFoundException, GenericException {
        return ResponseEntity.ok(userAccountServiceImpl.getUserById(id));
    }

    @Operation(summary = "Update an existing user account",
            description = "Updates the specified user account and returns the updated user account data.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user account data provided"),
            @ApiResponse(responseCode = "404", description = "User account not found"),
    })
    @PutMapping("/update")
    public ResponseEntity<UserAccountDTO> updateUser(
            @Parameter(description = "User account details to update") @RequestBody UserAccountDTO userAccountDTO
    ) throws UserNotFoundException, UserAccountValidationException, GenericException {
        return ResponseEntity.ok(userAccountServiceImpl.updateUser(userAccountDTO));
    }

    @Operation(summary = "Delete a user account",
            description = "Deletes the user account with the specified ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User account not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user account to delete") @PathVariable UUID id
    ) throws UserNotFoundException, GenericException {
        userAccountServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search users by name",
            description = "Retrieves a paginated list of user accounts matching the specified name.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User accounts retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter"),
    })
    @GetMapping("/search-by-name")
    public ResponseEntity<Page<UserAccountDTO>> searchUsersByName(
            @Parameter(description = "Name to search for user accounts") @RequestParam String name,
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 2, page = 0, sort = "name,asc") Pageable pageable
    ) throws JsonProcessingException, UserNotFoundException, GenericException {
        return ResponseEntity.ok(userAccountServiceImpl.searchUsersByName(name, pageable));
    }
}
