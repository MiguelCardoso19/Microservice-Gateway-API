package com.example.Exercicio15.service;

import com.example.Exercicio15.dto.UserAccountDTO;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.exception.user.UserAccountValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserAccountService {
    UserAccountDTO createUser(UserAccountDTO userAccountDto) throws UserAccountValidationException;
    UserAccountDTO getUserById(UUID id) throws UserAccountNotFoundException;
    Page<UserAccountDTO> getAllUsers(Pageable pageable);
    UserAccountDTO updateUser(UserAccountDTO userAccountDto) throws UserAccountNotFoundException, UserAccountValidationException;
    void deleteUser(UUID id) throws UserAccountNotFoundException;
    Page<UserAccountDTO> searchUsersByName(String name, Pageable pageable) throws UserAccountNotFoundException;
}