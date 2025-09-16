package com.finixone.party.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.finixone.party.dto.AccountDto;
import com.finixone.party.dto.AccountPartyDto;
import com.finixone.party.model.Party;

@Mapper
public interface PartyMapper {
 PartyMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PartyMapper.class);


    @Mapping(target = "partyName", source = "accountPartyDto.partyName")
    @Mapping(target = "email", source = "accountPartyDto.email")
    @Mapping(target = "primaryPhone", source = "accountPartyDto.primaryPhone")
    @Mapping(target = "primaryContactType", source = "accountPartyDto.primaryContactType")
    @Mapping(target = "partyType", source = "accountPartyDto.partyType")
    @Mapping(target = "partyStatus", source = "accountPartyDto.partyStatus")
    @Mapping(target = "country", source = "accountPartyDto.country")
    @Mapping(target = "currency", source = "accountDto.currency")
    @Mapping(target = "userResponsible", source = "accountDto.userResponsible")
    @Mapping(target = "startDate", source = "accountDto.startDate", qualifiedByName = "dateTimeToDate")
    @Mapping(target = "partyId", ignore = true)
    @Mapping(target = "alternativeId", ignore = true)
    Party createParty(AccountDto accountDto, AccountPartyDto accountPartyDto);
 

    @Named("dateTimeToDate")
    default LocalDate dateTimeToDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }
        
    }
