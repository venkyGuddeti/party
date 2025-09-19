package com.finixone.party.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private String operation;
    private UUID accountId;
    private UUID partyId;
    private String accountType;
    private String accountIdType;
    private String accountNumber;
    private String accountStatus;
    private String accountName;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean taxExempted;
    private Boolean allowMultiCurrency;
    private String companyCode;
    private String businessUnit;
    private String operatingUnit;
    private String costCenter;
    private String originalPartyId;
    private String segmentCode;
    private String accessGroup;
    private UUID userResponsible;
    private int billCycleDay;
    private String overridePayTerms;
    private Boolean billHold;
    private LocalDate billHoldDate;
    private Boolean collectionHold;
    private String activityTz;
    private String alternativeId;
    private String extensionJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
