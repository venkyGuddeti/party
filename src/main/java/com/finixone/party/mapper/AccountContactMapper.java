package com.finixone.party.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.finixone.party.dto.AccountContactDto;
import com.finixone.party.model.AccountContact;

@Mapper
public interface AccountContactMapper {
    AccountContactMapper INSTANCE = Mappers.getMapper(AccountContactMapper.class);

    // Map Account to AccountDto
    AccountContactDto toDto(AccountContact accountContact);

    // Map AccountDto to Account
    @InheritInverseConfiguration
    AccountContact toEntity(AccountContactDto accountContactDto);
}
