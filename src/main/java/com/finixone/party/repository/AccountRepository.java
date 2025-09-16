package com.finixone.party.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finixone.party.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    // Find by party ID
    List<Account> findByPartyId(UUID partyId);
    
    // Find by account number
    Optional<Account> findByAccountNumber(String accountNumber);
    
    // Find by account status
    List<Account> findByAccountStatus(String accountStatus);
    
    // Find by account type
    List<Account> findByAccountType(String accountType);
    
    // Find by company code
    List<Account> findByCompanyCode(String companyCode);
    
    // Find by business unit
    List<Account> findByBusinessUnit(String businessUnit);
    
    // Find by operating unit
    List<Account> findByOperatingUnit(String operatingUnit);
    
    
    // Find by user responsible
    List<Account> findByUserResponsible(UUID userResponsible);
    
    // Find active accounts (no end date or end date in future)
    @Query("SELECT a FROM Account a WHERE a.endDate IS NULL OR a.endDate > :currentDate")
    List<Account> findActiveAccounts(@Param("currentDate") LocalDateTime currentDate);
    
    // Find expired accounts
    @Query("SELECT a FROM Account a WHERE a.endDate IS NOT NULL AND a.endDate <= :currentDate")
    List<Account> findExpiredAccounts(@Param("currentDate") LocalDateTime currentDate);
    

    

    
    
    // Complex query: Find accounts by multiple criteria
    @Query("SELECT a FROM Account a ,Party b WHERE a.partyId = b.partyId AND " +
           "(:partyId IS NULL OR a.partyId = :partyId) AND " +
           "(:partyName IS NULL OR :partyName ='' OR b.partyNameUpper LIKE CONCAT('%', :partyName, '%')) AND " +
           "(:accountNumber IS NULL OR :accountNumber ='' OR a.accountNumber LIKE CONCAT('%', :accountNumber, '%')) AND " +
           "(:partyType IS NULL OR :partyType ='' OR b.partyType = :partyType) AND " +
           "(:accountStatus IS NULL OR a.accountStatus = :accountStatus) AND " +
           "(:accountType IS NULL OR a.accountType = :accountType) AND " +
           "(:companyCode IS NULL OR a.companyCode = :companyCode) AND " +
           "(:businessUnit IS NULL OR a.businessUnit = :businessUnit)")
    Page<Account> findAccountsByCriteria(
        @Param("partyId") UUID partyId,
        @Param("partyName") String partyName,
        @Param("partyType") String partyType,
        @Param("accountNumber") String accountNumber,
        @Param("accountStatus") String accountStatus,
        @Param("accountType") String accountType,
        @Param("companyCode") String companyCode,
        @Param("businessUnit") String businessUnit,
        Pageable pageable
    );
    
    // Count accounts by status
    Long countByAccountStatus(String accountStatus);
    
    // Count accounts by party
    Long countByPartyId(UUID partyId);
    
    // Find accounts created within date range
    @Query("SELECT a FROM Account a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    List<Account> findAccountsCreatedBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    // Check if account number exists
    Boolean existsByAccountNumber(String accountNumber);
    
    // Find accounts by cost center (not null)
    List<Account> findByCostCenterIsNotNull();
    
    // Find accounts with override pay terms
    List<Account> findByOverridePayTermsIsNotNull();
}
