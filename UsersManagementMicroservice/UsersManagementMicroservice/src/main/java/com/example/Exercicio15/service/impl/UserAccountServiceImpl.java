package com.example.Exercicio15.service.impl;

import com.example.Exercicio15.dto.UserAccountDTO;
import com.example.Exercicio15.exception.user.UserAccountNotFoundException;
import com.example.Exercicio15.exception.user.UserAccountValidationException;
import com.example.Exercicio15.mapper.UserAccountMapper;
import com.example.Exercicio15.model.UserAccount;
import com.example.Exercicio15.repository.UserAccountRepository;
import com.example.Exercicio15.service.UserAccountService;
import com.example.Exercicio15.validator.UserAccountValidator;

import static com.example.Exercicio15.util.LoggerUtil.log;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    @Override
    @Transactional
    public UserAccountDTO createUser(UserAccountDTO userAccountDTO) throws UserAccountValidationException {
        log.info("Creating user with NIF: {}", userAccountDTO.getNif());

        UserAccountValidator.checkForDuplicateUserAccountAttributes(userAccountDTO, userAccountRepository);

        UserAccount savedUser = userAccountRepository.save(userAccountMapper.toEntity(userAccountDTO));
        log.info("User created with NIF: {}", savedUser.getNif());
        return userAccountMapper.toDTO(savedUser);
    }

    @Override
    public UserAccountDTO getUserById(UUID id) throws UserAccountNotFoundException {
        log.info("Fetching user with ID: {}", id);
        UserAccount userAccount = userAccountRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}", id);
            return new UserAccountNotFoundException(id);
        });

        log.info("Retrieved user with ID: {}", id);
        return userAccountMapper.toDTO(userAccount);
    }

    @Override
    public Page<UserAccountDTO> getAllUsers(Pageable pageable) {
        log.info("Fetching all users with pagination - Page: {}, Size: {}, Sort: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Page<UserAccount> usersPage = userAccountRepository.findAll(pageable);

        log.info("Total users found: {}, Total pages: {}", usersPage.getTotalElements(), usersPage.getTotalPages());
        return usersPage.map(userAccountMapper::toDTO);
    }

    @Override
    public UserAccountDTO updateUser(UserAccountDTO userAccountDto) throws UserAccountNotFoundException, UserAccountValidationException {
        log.info("Updating user with ID: {}", userAccountDto.getId());

        UserAccount existingUser = userAccountRepository.findById(userAccountDto.getId())
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userAccountDto.getId());
                    return new UserAccountNotFoundException(userAccountDto.getId());
                });

        UserAccountValidator.checkForDuplicateUserAccountAttributes(userAccountDto, userAccountRepository);

        userAccountMapper.updateEntityFromDTO(userAccountDto, existingUser);
        UserAccount updatedUser = userAccountRepository.save(existingUser);

        log.info("User updated with ID: {}", updatedUser.getId());
        return userAccountMapper.toUserAccountDTOWithoutAddresses(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) throws UserAccountNotFoundException {
        log.info("Attempting to delete user with ID: {}", id);
        if (userAccountRepository.existsById(id)) {
            userAccountRepository.deleteById(id);
            log.info("User deleted with ID: {}", id);
            return;
        }
        log.error("User not found with ID: {}", id);
        throw new UserAccountNotFoundException(id);
    }

    @Override
    public Page<UserAccountDTO> searchUsersByName(String name, Pageable pageable) throws UserAccountNotFoundException {
        log.info("Searching users with name containing: {}", name);

        Page<UserAccount> userAccountsPage = userAccountRepository.findByNameContainingIgnoreCase(name, pageable);

        if (userAccountsPage.isEmpty()) {
            log.info("No users found with name containing: {}", name);
            throw new UserAccountNotFoundException(name);
        }

        log.info("Total users found: {}", userAccountsPage.getTotalElements());
        return userAccountsPage.map(userAccountMapper::toDTO);
    }
}