package com.finixone.party.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.finixone.party.dto.AccountPriceProfileDto;
import com.finixone.party.model.AccountPriceProfile;

@Mapper
public interface AccountPriceProfileMapper {
    AccountPriceProfileMapper INSTANCE = Mappers.getMapper(AccountPriceProfileMapper.class);
    // Map AccountPriceProfile to AccountPriceProfileDto
    AccountPriceProfileDto toDto(AccountPriceProfile accountPriceProfile);
    // AccountPriceProfileDto to AccountPriceProfile
    AccountPriceProfile toEntity(AccountPriceProfileDto accountPriceProfileDto);

}
