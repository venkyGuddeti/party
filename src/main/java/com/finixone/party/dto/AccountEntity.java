package com.finixone.party.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    String operation;
    AccountDto account;
   // List<AccountPartyDto> accountParties;
    List<AccountContactDto> accountContacts;
    List<AccountRelationshipDto> accountRelationships;
    List<AccountPriceProfileDto> priceProfiles;
}
