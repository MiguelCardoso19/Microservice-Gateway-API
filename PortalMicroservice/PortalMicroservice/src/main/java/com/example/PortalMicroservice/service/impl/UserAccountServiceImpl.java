package com.example.PortalMicroservice.service.impl;

import com.example.PortalMicroservice.client.UserAccountRestTemplateClient;
import com.example.PortalMicroservice.dto.UserAccountDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.exception.userManagement.UserAccountValidationException;
import com.example.PortalMicroservice.service.UserAccountService;
import com.example.PortalMicroservice.util.ClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRestTemplateClient userAccountRestTemplateClient;
    private final ObjectMapper objectMapper;

    @Override
    public UserAccountDTO createUser(UserAccountDTO userAccountDTO) throws UserAccountValidationException, GenericException {
        try {
            return userAccountRestTemplateClient.createUser(userAccountDTO).getBody();
        } catch (HttpClientErrorException e) {
            throw new UserAccountValidationException(e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new GenericException();
        }
    }

    @Override
    public UserAccountDTO getUserById(UUID id) throws UserNotFoundException, GenericException {
        try {
            return userAccountRestTemplateClient.getUserById(id).getBody();
        } catch (HttpClientErrorException e) {
            throw new UserNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new GenericException();
        }
    }

    @Override
    public UserAccountDTO updateUser(UserAccountDTO userAccountDTO) throws UserAccountValidationException, UserNotFoundException, GenericException {
        try {
            return userAccountRestTemplateClient.updateUser(userAccountDTO).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new UserNotFoundException(userAccountDTO.getId());
            }
            throw new UserAccountValidationException(e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new GenericException();
        }
    }

    @Override
    public void deleteUser(UUID id) throws UserNotFoundException, GenericException {
        try {
            userAccountRestTemplateClient.deleteUser(id);
        } catch (HttpClientErrorException e) {
            throw new UserNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new GenericException();
        }
    }

    @Override
    public Page<UserAccountDTO> searchUsersByName(String name, Pageable pageable) throws JsonProcessingException, UserNotFoundException, GenericException {
        try {
            ResponseEntity<String> response = userAccountRestTemplateClient.searchUsersByName(name, pageable);
            return ClientUtils.parsePage(objectMapper, response.getBody(), pageable, new TypeReference<>() {});
        } catch (HttpClientErrorException e) {
            throw new UserNotFoundException(name);
        } catch (HttpServerErrorException e) {
            throw new GenericException();
        }
    }
}