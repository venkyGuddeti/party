package com.finixone.party.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.finixone.party.dto.AccountRelationshipDto;
import com.finixone.party.model.AccountRelationship;

@Mapper 
public interface AccountRelationshipMapper {

    AccountRelationshipMapper INSTANCE = Mappers.getMapper(AccountRelationshipMapper.class);
   
    // Map AccountRelationship to AccountRelationshipDto
    AccountRelationshipDto toDto(AccountRelationship accountRelationship);
    // Map AccountRelationshipDto to AccountRelationship
    AccountRelationship toEntity(AccountRelationshipDto accountRelationshipDto);
}
