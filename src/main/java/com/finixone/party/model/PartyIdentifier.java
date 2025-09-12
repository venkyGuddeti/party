package com.finixone.party.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "party_identifier", schema = "fnx_party")
public class PartyIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "party_identifier_id", nullable = false)
    private UUID partyIdentifierId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "party_id_type", length = 50, nullable = false)
    private String partyIdType;

    @Column(name = "party_id_number", length = 255, nullable = false)
    private String partyIdNumber;

    @Column(name = "issued_by", length = 255)
    private String issuedBy;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "extension_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extensionJson; // store JSON as text; map to JsonNode if you use hibernate-types

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "updated_by", length = 255)
    private String updatedBy;

    @PrePersist
    void onCreate() {
        var now = LocalDateTime.now();
        if (createdAt == null)
            createdAt = now;
        if (updatedAt == null)
            updatedAt = now;
        if (createdBy == null)
            createdBy = "system";
        if (updatedBy == null)
            updatedBy = "system";
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (updatedBy == null)
            updatedBy = "system";
    }

}
