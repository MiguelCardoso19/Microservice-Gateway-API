package com.example.Exercicio15.service;

import com.example.Exercicio15.dto.AddressDTO;
import com.example.Exercicio15.exception.address.AddressNotFoundException;
import com.example.Exercicio15.exception.address.AddressValidationException;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO) throws UserAccountNotFoundException, AddressValidationException;
    AddressDTO updateAddress(AddressDTO addressDTO) throws AddressNotFoundException, AddressValidationException;
    void deleteAddress(UUID addressId) throws AddressNotFoundException;
    AddressDTO getAddressById(UUID addressId) throws AddressNotFoundException;
    Page<AddressDTO> getAllAddresses(Pageable pageable);
    List<AddressDTO> getAddressesByUserAccountId(UUID userId) throws UserAccountNotFoundException;
}