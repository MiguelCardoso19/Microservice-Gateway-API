package com.example.PortalMicroservice.service;

import com.example.PortalMicroservice.dto.UserAccountDTO;
import com.example.PortalMicroservice.exception.GenericException;
import com.example.PortalMicroservice.exception.authentication.InvalidPasswordException;
import com.example.PortalMicroservice.exception.authentication.UserCredentialsValidationException;
import com.example.PortalMicroservice.exception.UserNotFoundException;
import com.example.PortalMicroservice.exception.userManagement.UserAccountValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserAccountService {
    UserAccountDTO createUser(UserAccountDTO userAccountDTO) throws UserAccountValidationException, GenericException;
    UserAccountDTO getUserById(UUID id) throws UserNotFoundException, GenericException;
    UserAccountDTO updateUser(UserAccountDTO userAccountDTO) throws UserNotFoundException, InvalidPasswordException, UserCredentialsValidationException, UserAccountValidationException, GenericException;
    void deleteUser(UUID id) throws UserNotFoundException, GenericException;
    Page<UserAccountDTO> searchUsersByName(String name, Pageable pageable) throws JsonProcessingException, UserNotFoundException, GenericException;
}