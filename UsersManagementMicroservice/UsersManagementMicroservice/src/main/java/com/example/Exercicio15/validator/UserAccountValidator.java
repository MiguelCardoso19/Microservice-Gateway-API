package com.example.Exercicio15.validator;

import com.example.Exercicio15.dto.UserAccountDTO;
import com.example.Exercicio15.exception.user.UserAccountValidationException;
import com.example.Exercicio15.repository.UserAccountRepository;
import static com.example.Exercicio15.util.LoggerUtil.log;

import java.util.ArrayList;
import java.util.List;

public class UserAccountValidator {

    public static void checkForDuplicateUserAccountAttributes(UserAccountDTO userAccountDTO, UserAccountRepository userAccountRepository) throws UserAccountValidationException {
        log.info("Checking for existing user account conflicts for NIF: {}", userAccountDTO.getNif());
        List<String> errorMessages = new ArrayList<>();

        validateEmailForExistingUser(userAccountDTO, userAccountRepository, errorMessages);
        validatePhoneNumberForExistingUser(userAccountDTO, userAccountRepository, errorMessages);
        validateNifForExistingUser(userAccountDTO, userAccountRepository, errorMessages);

        if (!errorMessages.isEmpty()) {
            log.error("Conflicts found during validation for user account with NIF: {}. Errors: {}", userAccountDTO.getNif(), errorMessages);
            throw new UserAccountValidationException(errorMessages);
        }

        log.info("No conflicts found for user account with NIF: {}", userAccountDTO.getNif());
    }

    private static void validateEmailForExistingUser(UserAccountDTO userAccountDTO, UserAccountRepository userAccountRepository, List<String> errorMessages) {
        log.info("Checking for existing email: {}", userAccountDTO.getEmail());
        if (userAccountRepository.existsByEmail(userAccountDTO.getEmail())) {
            errorMessages.add("Email already exists: " + userAccountDTO.getEmail());
            log.error("Validation failed: Email already exists. Email: {}", userAccountDTO.getEmail());
        }
    }

    private static void validatePhoneNumberForExistingUser(UserAccountDTO userAccountDTO, UserAccountRepository userAccountRepository, List<String> errorMessages) {
        log.info("Checking for existing phone number: {}", userAccountDTO.getPhoneNumber());
        if (userAccountRepository.existsByPhoneNumber(userAccountDTO.getPhoneNumber())) {
            errorMessages.add("Phone number already exists: " + userAccountDTO.getPhoneNumber());
            log.error("Validation failed: Phone number already exists. Phone number: {}", userAccountDTO.getPhoneNumber());
        }
    }

    private static void validateNifForExistingUser(UserAccountDTO userAccountDTO, UserAccountRepository userAccountRepository, List<String> errorMessages) {
        log.info("Checking for existing NIF: {}", userAccountDTO.getNif());
        if (userAccountRepository.existsByNif(userAccountDTO.getNif())) {
            errorMessages.add("NIF already exists: " + userAccountDTO.getNif());
            log.error("Validation failed: NIF already exists. NIF: {}", userAccountDTO.getNif());
        }
    }
}