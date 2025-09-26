package com.finixone.party.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finixone.party.model.AccountPriceProfile;

@Repository
public interface AccountPriceProfileRepository extends JpaRepository<AccountPriceProfile, UUID> {
    
    /**
     * Find all account price profiles by account ID
     */
    List<AccountPriceProfile> findByAccountId(UUID accountId);
    
    /**
     * Find all account price profiles by price list ID
     */
    List<AccountPriceProfile> findByPriceListId(UUID priceListId);
    
    /**
     * Find by price list code
     */
    List<AccountPriceProfile> findByPriceListCode(String priceListCode);
    
    /**
     * Find by account ID and status
     */
    List<AccountPriceProfile> findByAccountIdAndAccountPlStatus(UUID accountId, String status);
    
    /**
     * Find active profiles for an account (where current date is between start and end date)
     */
    @Query("SELECT app FROM AccountPriceProfile app WHERE app.accountId = :accountId " +
           "AND app.startDate <= :currentDate " +
           "AND (app.endDate IS NULL OR app.endDate >= :currentDate)")
    List<AccountPriceProfile> findActiveProfilesByAccountId(@Param("accountId") UUID accountId, 
                                                           @Param("currentDate") LocalDateTime currentDate);
    
    /**
     * Find profiles by account ID ordered by priority sequence
     */
    List<AccountPriceProfile> findByAccountIdOrderByPrioritySequenceAsc(UUID accountId);
    
    /**
     * Find profiles that expire within a given date range
     */
    @Query("SELECT app FROM AccountPriceProfile app WHERE app.endDate BETWEEN :startDate AND :endDate")
    List<AccountPriceProfile> findExpiringProfiles(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);
    
    /**
     * Check if a profile exists with the unique constraint combination
     */
    boolean existsByPriceListIdAndStartDateAndAccountPlStatus(UUID priceListId, 
                                                             LocalDateTime startDate, 
                                                             String accountPlStatus);
    
    /**
     * Find by account ID and price list ID
     */
    List<AccountPriceProfile> findByAccountIdAndPriceListId(UUID accountId, UUID priceListId);
    
    /**
     * Find profiles by status
     */
    List<AccountPriceProfile> findByAccountPlStatus(String status);
    
    /**
     * Find profiles created by a specific user
     */
    List<AccountPriceProfile> findByCreatedBy(String createdBy);
    
    /**
     * Count active profiles for an account
     */
    @Query("SELECT COUNT(app) FROM AccountPriceProfile app WHERE app.accountId = :accountId " +
           "AND app.startDate <= :currentDate " +
           "AND (app.endDate IS NULL OR app.endDate >= :currentDate)")
    long countActiveProfilesByAccountId(@Param("accountId") UUID accountId, 
                                       @Param("currentDate") LocalDateTime currentDate);

       /**
        * Find Price Profiles by Account Number
        */
       @Query("SELECT app FROM AccountPriceProfile app  JOIN Account a ON app.accountId = a.id WHERE a.accountNumber = :accountNumber and a.accountIdType = :accountIdType order by app.prioritySequence")
       List<AccountPriceProfile> findByAccountNumber(@Param("accountIdType") String accountIdType, @Param("accountNumber") String accountNumber);  

}
