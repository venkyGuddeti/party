package com.finixone.party.model;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_price_profile", schema = "fnx_party")
public class AccountPriceProfile {
    
     @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_price_profile_id", columnDefinition = "uuid")
    private UUID accountPriceProfileId;
    
    @Column(name = "account_id", nullable = false)
    private UUID accountId;
    
    @Column(name = "price_list_id", nullable = false)
    private UUID priceListId;
    
    @Column(name = "price_list_code", nullable = false, length = 255)
    private String priceListCode;
    
    @Column(name = "acount_pl_status", nullable = false, length = 10)
    private String accountPlStatus;
    
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "priorty_sequence")
    private Integer prioritySequence;
    
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
