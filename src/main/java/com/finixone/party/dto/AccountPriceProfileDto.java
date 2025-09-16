package com.finixone.party.dto;

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
public class AccountPriceProfileDto {
    private String operation;
    private UUID accountPriceProfileId;
    private UUID accountId;
    private UUID priceListId;
    private String priceListCode;
    private String accountPlStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer prioritySequence;
    private String extensionJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
