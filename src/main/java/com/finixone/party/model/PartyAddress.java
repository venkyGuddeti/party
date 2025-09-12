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
@Table(name = "party_address", schema = "fnx_party")
public class PartyAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "party_address_id", nullable = false)
    private UUID partyAddressId;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "address_type", length = 10, nullable = false)
    private String addressType;

    @Column(name = "address_status", length = 10, nullable = false)
    private String addressStatus;
    @Column(name = "address_line1", length = 255, nullable = false)
    private String addressLine1;
    @Column(name = "address_line2", length = 255)
    private String addressLine2;
    @Column(name = "city", length = 100, nullable = false)
    private String city;
    @Column(name = "state", length = 100)
    private String state;
    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;
    @Column(name = "country", length = 5, nullable = false)
    private String country;
     @Column(name="start_date", nullable = false)
    private LocalDate startDate;
    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name = "extension_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extensionJson; // store JSON as text; map to JsonNode if you use

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
