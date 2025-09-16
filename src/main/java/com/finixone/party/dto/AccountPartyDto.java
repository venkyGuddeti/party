package com.finixone.party.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountPartyDto {
    private String operation;
    private UUID accountId;
    private UUID partyId;
    private String partyName;
    private String partyType;
    private String partyStatus;
    private String primaryIdType;
    private String primaryIdNumber;
    private String alternativeId;
    private String email;
    private String primaryPhone;
    private String primaryContactType;
    private String country;  
    private String currency;
    private boolean isPrimary;
    private boolean isFinanicallyResponsible;
    private String partyRole;


}
