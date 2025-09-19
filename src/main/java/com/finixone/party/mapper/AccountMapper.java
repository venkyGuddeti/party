package com.finixone.party.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.finixone.party.dto.AccountDto;
import com.finixone.party.model.Account;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
   // @Mapping(target = "operation", ignore = true)
    // Map Account to AccountDto
    AccountDto toDto(Account account);

    // Map AccountDto to Account
    @InheritInverseConfiguration
    Account toEntity(AccountDto accountDto);
}
