package com.example.AuthenticationMicroservice.service.impl;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.exception.InvalidPasswordException;
import com.example.AuthenticationMicroservice.exception.UserCredentialsValidationException;
import com.example.AuthenticationMicroservice.exception.UserNotFoundException;
import com.example.AuthenticationMicroservice.mapper.DTOMapper;
import com.example.AuthenticationMicroservice.model.UserCredentials;
import com.example.AuthenticationMicroservice.repository.UserCredentialsRepository;
import com.example.AuthenticationMicroservice.service.JwtService;
import com.example.AuthenticationMicroservice.service.UserCredentialsService;
import com.example.AuthenticationMicroservice.validator.UserCredentialsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {
    private final UserCredentialsRepository userCredentialsRepository;
    private final DTOMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO register(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsValidationException {
        UserCredentialsValidator.validateUserCredentials(userCredentialsDTO, userCredentialsRepository);

        UserCredentials newUser = dtoMapper.toEntity(userCredentialsDTO, passwordEncoder);
        userCredentialsRepository.save(newUser);

        String token = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);
        return dtoMapper.toDTO(refreshToken, token, newUser.getId());
    }

    @Override
    public void delete(UserCredentialsDTO userCredentialsDTO) throws InvalidPasswordException, UserNotFoundException {
        UserCredentials existingUser = userCredentialsRepository.findById(userCredentialsDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userCredentialsDTO.getId()));

        if (!passwordEncoder.matches(userCredentialsDTO.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        userCredentialsRepository.delete(existingUser);
    }

    @Override
    public UserCredentialsDTO update(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsValidationException, InvalidPasswordException, UserNotFoundException {
        UserCredentials existingUser = userCredentialsRepository.findById(userCredentialsDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userCredentialsDTO.getId()));

        if (!passwordEncoder.matches(userCredentialsDTO.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        UserCredentialsValidator.validateUserCredentials(userCredentialsDTO, userCredentialsRepository);

        dtoMapper.updateFromDTO(userCredentialsDTO, existingUser, passwordEncoder);

        return dtoMapper.toDTO(userCredentialsRepository.save(existingUser));
    }

    @Override
    public Optional<UserCredentials> findByUsername(String username) {
        return userCredentialsRepository.findByUsername(username);
    }
}