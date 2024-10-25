package com.example.Exercicio15.service.impl;

import com.example.Exercicio15.dto.AddressDTO;
import com.example.Exercicio15.exception.address.AddressNotFoundException;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.mapper.AddressMapper;
import com.example.Exercicio15.model.Address;
import com.example.Exercicio15.model.UserAccount;
import com.example.Exercicio15.repository.AddressRepository;
import com.example.Exercicio15.repository.UserAccountRepository;
import com.example.Exercicio15.service.AddressService;

import static com.example.Exercicio15.util.LoggerUtil.log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserAccountRepository userAccountRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper, UserAccountRepository userAccountRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) throws UserAccountNotFoundException {
        log.info("Creating address for user with ID: {}", addressDTO.getUser().getId());

        UserAccount userAccount = userAccountRepository.findById(addressDTO.getUser().getId())
                .orElseThrow(() -> {
                    log.error("User account not found for user with ID: {}", addressDTO.getUser().getId());
                    return new UserAccountNotFoundException(addressDTO.getUser().getId());
                });

        Address address = addressMapper.toEntity(addressDTO);
        address.setUserAccount(userAccount);
        Address savedAddress = addressRepository.save(address);

        log.info("Address created for user with ID: {} and address ID: {}", addressDTO.getUser().getId(), savedAddress.getId());
        return addressMapper.toDTO(savedAddress);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) throws AddressNotFoundException {
        log.info("Updating address with ID: {}", addressDTO.getId());

        Address existingAddress = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> {
                    log.error("Address does not exist with ID: {}", addressDTO.getId());
                    return new AddressNotFoundException(addressDTO.getId());
                });

        addressMapper.updateEntityFromDTO(addressDTO, existingAddress);

        Address updatedAddress = addressRepository.save(existingAddress);

        log.info("Address updated with ID: {}", updatedAddress.getId());
        return addressMapper.toDTOWithoutUser(updatedAddress);
    }

    @Override
    public void deleteAddress(UUID addressId) throws AddressNotFoundException {
        log.info("Attempting to delete address with ID: {}", addressId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> {
                    log.error("Address not found with ID: {} ", addressId);
                    return new AddressNotFoundException(addressId);
                });

        UserAccount user = address.getUserAccount();
        user.removeAddress(address);

        addressRepository.delete(address);
        log.info("Successfully deleted address with ID: {}", addressId);
    }

    @Override
    public AddressDTO getAddressById(UUID addressId) throws AddressNotFoundException {
        log.info("Fetching address with ID: {}", addressId);
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> {
                    log.error("Address not found with ID: {} ", addressId);
                    return new AddressNotFoundException(addressId);
                });

        return addressMapper.toDTO(existingAddress);
    }

    @Override
    public Page<AddressDTO> getAllAddresses(Pageable pageable) {
        log.info("Fetching all addresses with pagination");

        Page<Address> addressPage = addressRepository.findAll(pageable);

        log.info("Total addresses found: {}", addressPage.getTotalElements());
        return addressPage.map(addressMapper::toDTO);
    }

    @Override
    public List<AddressDTO> getAddressesByUserAccountId(UUID userId) throws UserAccountNotFoundException {
        log.info("Fetching addresses for User Account ID: {}", userId);
        List<Address> addresses = addressRepository.findByUserAccountId(userId);
        if (addresses.isEmpty()) {
            log.warn("No addresses found for User Account ID: {}", userId);
            throw new UserAccountNotFoundException(userId);
        }

        return addresses.stream()
                .map(addressMapper::toDTOWithoutUser)
                .collect(Collectors.toList());
    }
}