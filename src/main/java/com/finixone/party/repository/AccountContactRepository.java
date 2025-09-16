package com.finixone.party.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finixone.party.model.AccountContact;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountContactRepository extends JpaRepository<AccountContact, UUID> {

    /**
     * Find all contacts by account ID
     */
    List<AccountContact> findByAccountId(UUID accountId);

    /**
     * Find contacts by account ID with pagination
     */
    Page<AccountContact> findByAccountId(UUID accountId, Pageable pageable);

    /**
     * Find contacts by account ID and contact type
     */
    List<AccountContact> findByAccountIdAndContactType(UUID accountId, String contactType);

    /**
     * Find contacts by contact status
     */
    List<AccountContact> findByContactStatus(String contactStatus);

    /**
     * Find active contacts (no end date or end date in future)
     */
    @Query("SELECT ac FROM AccountContact ac WHERE ac.accountId = :accountId " +
           "AND (ac.endDate IS NULL OR ac.endDate >= CURRENT_DATE)")
    List<AccountContact> findActiveContactsByAccountId(@Param("accountId") UUID accountId);

    /**
     * Find contacts by email
     */
    Optional<AccountContact> findByEmail(String email);

    /**
     * Find contacts by entity name (case-insensitive)
     */
    List<AccountContact> findByEntityNameContainingIgnoreCase(String entityName);

    /**
     * Find contacts by country
     */
    List<AccountContact> findByCountry(String country);

    /**
     * Find contacts by city and state
     */
    List<AccountContact> findByCityAndState(String city, String state);

    /**
     * Find contacts created between dates
     */
    @Query("SELECT ac FROM AccountContact ac WHERE ac.createdAt BETWEEN :startDate AND :endDate")
    List<AccountContact> findContactsCreatedBetween(@Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);

    /**
     * Count contacts by account ID
     */
    long countByAccountId(UUID accountId);

    /**
     * Count active contacts by account ID
     */
    @Query("SELECT COUNT(ac) FROM AccountContact ac WHERE ac.accountId = :accountId " +
           "AND (ac.endDate IS NULL OR ac.endDate >= CURRENT_DATE)")
    long countActiveContactsByAccountId(@Param("accountId") UUID accountId);

    /**
     * Check if email exists for different contact
     */
    boolean existsByEmailAndContactIdNot(String email, UUID contactId);

    /**
     * Find contacts expiring within specified days
     */
    @Query("SELECT ac FROM AccountContact ac WHERE ac.endDate IS NOT NULL " +
           "AND ac.endDate BETWEEN CURRENT_DATE AND :expiryDate")
    List<AccountContact> findContactsExpiringBefore(@Param("expiryDate") LocalDate expiryDate);

    /**
     * Soft delete by setting end date
     */
    @Modifying
    @Query("UPDATE AccountContact ac SET ac.endDate = CURRENT_DATE, ac.updatedBy = :updatedBy " +
           "WHERE ac.contactId = :contactId")
    int softDeleteContact(@Param("contactId") UUID contactId, @Param("updatedBy") String updatedBy);

    /**
     * Update contact status
     */
    @Modifying
    @Query("UPDATE AccountContact ac SET ac.contactStatus = :status, ac.updatedBy = :updatedBy " +
           "WHERE ac.contactId = :contactId")
    int updateContactStatus(@Param("contactId") UUID contactId, 
                           @Param("status") String status, 
                           @Param("updatedBy") String updatedBy);

    /**
     * Find contacts by postal code pattern
     */
    List<AccountContact> findByPostalCodeStartingWith(String postalCodePrefix);

    /**
     * Custom query to find contacts with complex search criteria
     */
    @Query("SELECT ac FROM AccountContact ac WHERE " +
           "(:accountId IS NULL OR ac.accountId = :accountId) AND " +
           "(:contactType IS NULL OR ac.contactType = :contactType) AND " +
           "(:contactStatus IS NULL OR ac.contactStatus = :contactStatus) AND " +
           "(:entityName IS NULL OR LOWER(ac.entityName) LIKE LOWER(CONCAT('%', :entityName, '%'))) AND " +
           "(:email IS NULL OR LOWER(ac.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:city IS NULL OR LOWER(ac.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
           "(:state IS NULL OR LOWER(ac.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
           "(:country IS NULL OR ac.country = :country)")
    Page<AccountContact> findContactsWithCriteria(@Param("accountId") UUID accountId,
                                                 @Param("contactType") String contactType,
                                                 @Param("contactStatus") String contactStatus,
                                                 @Param("entityName") String entityName,
                                                 @Param("email") String email,
                                                 @Param("city") String city,
                                                 @Param("state") String state,
                                                 @Param("country") String country,
                                                 Pageable pageable);
}