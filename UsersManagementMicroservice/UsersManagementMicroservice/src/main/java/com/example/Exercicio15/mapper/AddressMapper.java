package com.example.Exercicio15.mapper;

import com.example.Exercicio15.dto.AddressDTO;
import com.example.Exercicio15.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserAccountMapper.class)
public interface AddressMapper {

    @Mapping(target = "user", source = "userAccount", qualifiedByName = "toUserWithoutAddresses")
    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);

    @Mapping(target = "user", ignore = true)
    AddressDTO toDTOWithoutUser(Address address);

    void updateEntityFromDTO(AddressDTO dto, @MappingTarget Address entity);
}