package com.finixone.party.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonFormat;

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

@Entity
@Table(name = "account_contact", schema = "fnx_party")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountContact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contact_id", columnDefinition = "uuid")
    private UUID contactId;

    @Column(name = "account_id", nullable = false, columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "contact_type", nullable = false, length = 10)
    private String contactType;

    @Column(name = "entity_name", nullable = false, length = 255)
    private String entityName;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "contact_status", nullable = false, length = 10)
    private String contactStatus;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "address_line3", length = 255)
    private String addressLine3;

    @Column(name = "address_line4", length = 255)
    private String addressLine4;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "county", length = 255)
    private String county;

    @Column(name = "country", length = 5)
    private String country;

    @Column(name = "postal_code", length = 255)
    private String postalCode;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "extension_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extensionJson;

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
