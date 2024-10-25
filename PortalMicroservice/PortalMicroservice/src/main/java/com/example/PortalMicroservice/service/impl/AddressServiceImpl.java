package com.example.PortalMicroservice.service.impl;

import com.example.PortalMicroservice.dto.AddressDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.userManagement.AddressNotFoundException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.client.AddressApacheHttpClient;
import com.example.PortalMicroservice.service.AddressService;
import com.example.PortalMicroservice.util.ClientUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ObjectMapper objectMapper;
    private final AddressApacheHttpClient addressApacheHttpClient;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) throws Exception {
        HttpResponse response = addressApacheHttpClient.createAddress(addressDTO);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new UserNotFoundException(addressDTO.getUser().getId());
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new GenericException();
        }

        return objectMapper.readValue(response.getEntity().getContent(), AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) throws AddressNotFoundException, IOException, GenericException {
        HttpResponse response = addressApacheHttpClient.updateAddress(addressDTO);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new AddressNotFoundException(addressDTO.getId());
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new GenericException();
        }

        return objectMapper.readValue(response.getEntity().getContent(), AddressDTO.class);
    }

    @Override
    public void deleteAddress(UUID addressId) throws IOException, AddressNotFoundException, GenericException {
        HttpResponse response = addressApacheHttpClient.deleteAddress(addressId);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new AddressNotFoundException(addressId);
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new GenericException();
        }
    }

    @Override
    public AddressDTO getAddressById(UUID addressId) throws Exception {
        HttpResponse response = addressApacheHttpClient.getAddressById(addressId);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new AddressNotFoundException(addressId);
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new GenericException();
        }
        return objectMapper.readValue(response.getEntity().getContent(), AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressesByUserId(UUID userId) throws Exception {
        HttpResponse response = addressApacheHttpClient.getAddressesByUserId(userId);

        if (response.getStatusLine().getStatusCode() == 404) {
            throw new UserNotFoundException(userId);
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new GenericException();
        }

        return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<>() {});
    }

    @Override
    public Page<AddressDTO> getAllAddresses(Pageable pageable) throws IOException {
        HttpResponse response = addressApacheHttpClient.getAllAddresses(pageable);
        return ClientUtils.parsePage(objectMapper, response.getEntity().getContent().toString(), pageable, new TypeReference<>() {});
    }
}