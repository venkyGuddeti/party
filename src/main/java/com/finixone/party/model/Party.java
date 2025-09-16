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
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "party", schema = "fnx_party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "party_name", length = 255, nullable = false)
    private String partyName;
    @Column(name = "party_name_upper", length = 255, nullable = false)
    private String partyNameUpper;

    @Column(name = "party_type", length = 10, nullable = false)
    private String partyType;
    @Column(name = "party_status", length = 10, nullable = false)
    private String partyStatus;
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
    @Column(name="currency", length = 5)
    private String currency;
    @Column(name="start_date", nullable = false)
    private LocalDate startDate;
    @Column(name="primary_id_type", length = 50,nullable = false)
    private String primaryIdType;
    @Column(name="primary_id_number", length = 255,nullable = false)
    private String primaryIdNumber;
    @Column(name="alternative_id", length = 255)
    private String alternativeId;

    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "primary_phone", length = 20)
    private String primaryPhone;

    @Column(name = "primary_contact_type", length = 50)
    private String primaryContactType;
    @Column(name = "segment", length = 50)
    private String segment;

    @Column(name = "user_responsible",columnDefinition = "uuid")
    private UUID userResponsible;

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
      if (createdAt == null) createdAt = now;
      if (updatedAt == null) updatedAt = now;
      if (createdBy == null) createdBy = "system";
      if (updatedBy == null) updatedBy = "system";
    }

    @PreUpdate
    void onUpdate() {
      updatedAt = LocalDateTime.now();
      if (updatedBy == null) updatedBy = "system";
    }

}
