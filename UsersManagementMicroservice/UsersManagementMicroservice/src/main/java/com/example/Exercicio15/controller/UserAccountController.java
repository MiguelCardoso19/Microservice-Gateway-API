package com.example.Exercicio15.controller;

import com.example.Exercicio15.dto.UserAccountDTO;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.exception.user.UserAccountValidationException;
import com.example.Exercicio15.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Account API", description = "Operations for managing user accounts, including creation, updating, retrieval, and deletion.")
@Validated
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Operation(summary = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<UserAccountDTO> createUser(
            @Parameter(description = "User account data") @Valid @RequestBody UserAccountDTO userAccountDto)
            throws UserAccountValidationException {
        return ResponseEntity.status(CREATED).body(userAccountService.createUser(userAccountDto));
    }

    @Operation(summary = "Get user account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account found"),
            @ApiResponse(responseCode = "404", description = "User account not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> getUserById(@Parameter(description = "User ID") @PathVariable UUID id)
            throws UserAccountNotFoundException {
        return ResponseEntity.ok(userAccountService.getUserById(id));
    }

    @Operation(summary = "Get all user accounts with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of user accounts")
    })
    @GetMapping("/all-users")
    public ResponseEntity<Page<UserAccountDTO>> getAllUsers(
            @PageableDefault(size = 10, page = 0, sort = "name,asc") Pageable pageable
    ) {
        return ResponseEntity.ok(userAccountService.getAllUsers(pageable));
    }

    @Operation(summary = "Update an existing user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account updated successfully"),
            @ApiResponse(responseCode = "404", description = "User account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/update")
    public ResponseEntity<UserAccountDTO> updateUser(
            @RequestBody @Valid @Parameter(description = "Updated user account data") UserAccountDTO userAccountDTO)
            throws UserAccountNotFoundException, UserAccountValidationException {
        return ResponseEntity.ok(userAccountService.updateUser(userAccountDTO));
    }

    @Operation(summary = "Delete a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User account not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Parameter(description = "User ID to delete") UUID id)
            throws UserAccountNotFoundException {
        userAccountService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search users by name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users matching the search criteria"),
            @ApiResponse(responseCode = "404", description = "No users found with the given name"),
    })
    @GetMapping("/search-by-name")
    public ResponseEntity<Page<UserAccountDTO>> searchUsers(
            @RequestParam @Parameter(description = "User name to search for") String name,
            @PageableDefault(size = 10, page = 0, sort = "name,asc") Pageable pageable
    ) throws UserAccountNotFoundException {
        return ResponseEntity.ok(userAccountService.searchUsersByName(name, pageable));
    }
}