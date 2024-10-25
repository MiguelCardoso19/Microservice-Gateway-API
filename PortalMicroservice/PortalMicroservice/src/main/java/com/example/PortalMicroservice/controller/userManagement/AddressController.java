package com.example.PortalMicroservice.controller.userManagement;

import com.example.PortalMicroservice.dto.AddressDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.userManagement.AddressNotFoundException;
import com.example.PortalMicroservice.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/proxy/addresses")
@RequiredArgsConstructor
@Tag(name = "Address Management API", description = "API for managing addresses, including creation, update, deletion, and retrieval.")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Create a new address",
            description = "Creates a new address and returns the created address data.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid address data provided"),
    })
    @PostMapping("/create")
    public ResponseEntity<AddressDTO> createAddress(
            @Valid @RequestBody @Parameter(description = "Address details to create") AddressDTO addressDTO
    ) throws Exception {
        return ResponseEntity.status(201).body(addressService.createAddress(addressDTO));
    }

    @Operation(summary = "Update an existing address",
            description = "Updates the given address and returns the updated address details.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid address data provided"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
    })
    @PutMapping("/update")
    public ResponseEntity<AddressDTO> updateAddress(
            @Valid @RequestBody @Parameter(description = "Address details to update") AddressDTO addressDTO
    ) throws Exception {
        return ResponseEntity.ok(addressService.updateAddress(addressDTO));
    }

    @Operation(summary = "Delete an address",
            description = "Deletes the address with the specified ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
    })
    @DeleteMapping("/{address-id}")
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID of the address to delete") @PathVariable(value = "address-id") UUID addressId
    ) throws IOException, AddressNotFoundException, GenericException {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get address by ID",
            description = "Retrieves the details of an address by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
    })
    @GetMapping("/{address-id}")
    public ResponseEntity<AddressDTO> getAddressById(
            @Parameter(description = "ID of the address to retrieve") @PathVariable(value = "address-id") UUID addressId
    ) throws Exception {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @Operation(summary = "Get addresses by user ID",
            description = "Retrieves all addresses associated with a specific user ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No addresses found for the user ID"),
    })
    @GetMapping("/get-by-user-id/{user-id}")
    public ResponseEntity<List<AddressDTO>> getAddressesByUserAccountId(
            @Parameter(description = "User ID to retrieve addresses for") @PathVariable(value = "user-id") UUID userId
    ) throws Exception {
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
    }

    @Operation(summary = "Get all addresses with pagination",
            description = "Retrieves all addresses, with optional pagination and sorting.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
    })
    @GetMapping("/all")
    public ResponseEntity<Page<AddressDTO>> getAllAddresses(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 2, page = 0, sort = "street,asc") Pageable pageable
    ) throws IOException {
        return ResponseEntity.ok(addressService.getAllAddresses(pageable));
    }
}