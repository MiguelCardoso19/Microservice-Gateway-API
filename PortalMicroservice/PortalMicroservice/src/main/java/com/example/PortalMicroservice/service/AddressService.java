package com.example.PortalMicroservice.service;

import com.example.PortalMicroservice.dto.AddressDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.userManagement.AddressNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO) throws Exception;
    AddressDTO updateAddress(AddressDTO addressDTO) throws Exception;
    void deleteAddress(UUID addressId) throws IOException, AddressNotFoundException, GenericException;
    AddressDTO getAddressById(UUID addressId) throws Exception;
    List<AddressDTO> getAddressesByUserId(UUID userId) throws Exception;
    Page<AddressDTO> getAllAddresses(Pageable pageable) throws IOException;
}