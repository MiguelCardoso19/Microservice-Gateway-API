package com.example.AuthenticationMicroservice.validator;

import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.UserCredentialsValidationException;
import com.example.AuthenticationMicroservice.repository.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserCredentialsValidator {

    public static void validateUserCredentials(UserCredentialsDTO userCredentialsDTO,
                                               UserCredentialsRepository repository) throws UserCredentialsValidationException {

        List<String> errorMessages = new ArrayList<>();

        validateUsernameForExistingUser(userCredentialsDTO, repository, errorMessages);
        validateEmailForExistingUser(userCredentialsDTO, repository, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new UserCredentialsValidationException(errorMessages);
        }
    }

    private static void validateUsernameForExistingUser(UserCredentialsDTO userCredentialsDTO,
                                                        UserCredentialsRepository repository,
                                                        List<String> errorMessages) {
        log.info("Checking for existing username: {}", userCredentialsDTO.getUsername());

        if (repository.existsByUsernameAndIdNot(userCredentialsDTO.getUsername(), userCredentialsDTO.getId())) {
            errorMessages.add("Username already exists: " + userCredentialsDTO.getUsername());
            log.error("Validation failed: Username already exists. Username: {}", userCredentialsDTO.getUsername());
        }
    }

    private static void validateEmailForExistingUser(UserCredentialsDTO userCredentialsDTO,
                                                     UserCredentialsRepository repository,
                                                     List<String> errorMessages) {
        log.info("Checking for existing email: {}", userCredentialsDTO.getEmail());

        if (repository.existsByEmailAndIdNot(userCredentialsDTO.getEmail(), userCredentialsDTO.getId())) {
            errorMessages.add("Email already exists: " + userCredentialsDTO.getEmail());
            log.error("Validation failed: Email already exists. Email: {}", userCredentialsDTO.getEmail());
        }
    }
}