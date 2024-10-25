package com.example.AuthenticationMicroservice.mapper;

import com.example.AuthenticationMicroservice.dto.AuthenticationResponseDTO;
import com.example.AuthenticationMicroservice.dto.UserCredentialsDTO;
import com.example.AuthenticationMicroservice.model.UserCredentials;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DTOMapper {

    UserCredentialsDTO toDTO(UserCredentials userCredentials);

    @Mapping(target = "password", expression = "java(encodePassword(userCredentialsDTO.getPassword(), passwordEncoder))")
    UserCredentials toEntity(UserCredentialsDTO userCredentialsDTO, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(encodePassword(userCredentialsDTO.getNewPassword(), passwordEncoder))")
    void updateFromDTO(UserCredentialsDTO userCredentialsDTO, @MappingTarget UserCredentials entity, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "id", ignore = true)
    AuthenticationResponseDTO toDTOWithoutUserID(String token, String refreshToken);

    AuthenticationResponseDTO toDTO(String token, String refreshToken, UUID id);

    default String encodePassword(String password, PasswordEncoder passwordEncoder) {
        return (password != null && !password.isEmpty()) ? passwordEncoder.encode(password) : null;
    }
}