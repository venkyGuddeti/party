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
import lombok.Data;

@Entity
@Table(name = "account", schema = "fnx_party")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "party_id", columnDefinition = "uuid", nullable = false)
    private UUID partyId;

    @Column(name = "account_type", nullable = false, length = 10)
    private String accountType;

    @Column(name = "account_id_type", nullable = false, length = 10)
    private String accountIdType;

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;

    @Column(name = "account_status", nullable = false, length = 10)
    private String accountStatus;



    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Column(name = "currency", nullable = false, length = 5)
    private String currency;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "tax_exempted", nullable = false)
    private Boolean taxExempted = false;

    @Column(name = "allow_multi_currency", nullable = false)
    private Boolean allowMultiCurrency = false;

    // @Column(name = "auto_collection_sw", nullable = false, length = 1)
    // private String autoCollectionSw = "N";

    @Column(name = "company_code", length = 50)
    private String companyCode;

    @Column(name = "business_unit",  length = 30)
    private String businessUnit;

    @Column(name = "operating_unit", length = 30)
    private String operatingUnit;

    @Column(name = "cost_center", length = 10)
    private String costCenter;

    // @Column(name = "original_party_id", nullable = false, length = 50)
    // private String originalPartyId;

    @Column(name = "segment_code", length = 10)
    private String segmentCode;

    @Column(name = "access_group",length = 50)
    private String accessGroup;

    @Column(name = "user_responsible", columnDefinition = "uuid")
    private UUID userResponsible;

    @Column(name = "bill_cycle_day")
    private int billCycleDay;

    @Column(name = "override_pay_terms", length = 50)
    private String overridePayTerms;

    @Column(name = "bill_hold")
    private Boolean billHold;

    @Column(name = "bill_hold_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate billHoldDate;

    @Column(name = "collection_hold")
    private Boolean collectionHold;

    @Column(name = "activity_tz", length = 50)
    private String activityTz;

     @Column(name="alternative_id", length = 255)
    private String alternativeId;

    @Column(name = "extension_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String extensionJson;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by", length = 255)
    private String createdBy = "system";

    @Column(name = "updated_by", length = 255)
    private String updatedBy = "system";

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
