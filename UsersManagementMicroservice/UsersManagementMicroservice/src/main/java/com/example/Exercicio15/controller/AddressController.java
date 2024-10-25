package com.example.Exercicio15.controller;

import com.example.Exercicio15.dto.AddressDTO;
import com.example.Exercicio15.exception.address.AddressNotFoundException;
import com.example.Exercicio15.exception.address.AddressValidationException;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Address API", description = "Operations related to address management, including creation, updating, retrieval, and deletion of addresses associated with users.")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Create a new address for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/create")
    public ResponseEntity<AddressDTO> createAddress(
            @Valid @RequestBody @Parameter(description = "Address data to create") AddressDTO addressDTO
    ) throws UserAccountNotFoundException, AddressValidationException {
        return ResponseEntity.status(CREATED).body(addressService.createAddress(addressDTO));
    }

    @Operation(summary = "Update an existing address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(
            @Valid @RequestBody @Parameter(description = "Updated address data") AddressDTO addressDTO
    ) throws AddressNotFoundException, AddressValidationException {
        return ResponseEntity.ok(addressService.updateAddress(addressDTO));
    }

    @Operation(summary = "Delete an address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/{address-id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable(value = "address-id") @Parameter(description = "Address ID to delete") UUID addressId
    ) throws AddressNotFoundException {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get an address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{address-id}")
    public ResponseEntity<AddressDTO> getAddressById(
            @PathVariable(value = "address-id") @Parameter(description = "Address ID to retrieve") UUID addressId
    ) throws AddressNotFoundException {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @Operation(summary = "Get all addresses with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of addresses")
    })
    @GetMapping("/all")
    public ResponseEntity<Page<AddressDTO>> getAllAddresses(
            @PageableDefault(size = 10, page = 0, sort = "street,asc") Pageable pageable
    ) {
        return ResponseEntity.ok(addressService.getAllAddresses(pageable));
    }

    @Operation(summary = "Get all addresses for a specific user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of addresses for the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/get-by-user-id/{user-id}")
    public ResponseEntity<List<AddressDTO>> getAddressesByUserAccountId(
            @PathVariable(value = "user-id") @Parameter(description = "User ID to retrieve addresses for") UUID userId
    ) throws UserAccountNotFoundException {
        return ResponseEntity.ok(addressService.getAddressesByUserAccountId(userId));
    }
}