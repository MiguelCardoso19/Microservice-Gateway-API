package com.example.Exercicio15.mapper;

import com.example.Exercicio15.dto.UserAccountDTO;
import com.example.Exercicio15.model.UserAccount;
import com.example.Exercicio15.util.UserAccountUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = UserAccountUtil.class)
public interface UserAccountMapper {

    UserAccount toEntity(UserAccountDTO userAccountDTO);

    @Mapping(source = "name", target = "name", qualifiedByName = "capitalizeName")
    UserAccountDTO toDTO(UserAccount userAccount);

    @Named("toUserWithoutAddresses")
    @Mapping(target = "addresses", ignore = true)
    UserAccountDTO toUserAccountDTOWithoutAddresses(UserAccount userAccount);

    void updateEntityFromDTO(UserAccountDTO dto, @MappingTarget UserAccount entity);
}