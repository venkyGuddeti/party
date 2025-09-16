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
public class AccountContactDto {
    private String operation;
    private UUID contactId;
    private UUID accountId;
    private String contactType;
    private String entityName;
    private String email;
    private String contactStatus;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String county;
    private String country;
    private String postalCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String extensionJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

}
