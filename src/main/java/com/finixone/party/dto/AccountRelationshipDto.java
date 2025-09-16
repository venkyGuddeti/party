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
public class AccountRelationshipDto {
    String operation;
    private UUID accountRelationshipId;
    private UUID accountId;
    private UUID relatedAccountId;
    private String relationshipType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String extensionJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
