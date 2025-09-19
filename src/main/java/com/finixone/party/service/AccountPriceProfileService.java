package com.finixone.party.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finixone.party.model.AccountPriceProfile;
import com.finixone.party.repository.AccountPriceProfileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class AccountPriceProfileService {
    
    private final AccountPriceProfileRepository repository;
    
    @Autowired
    public AccountPriceProfileService(AccountPriceProfileRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Create a new account price profile
     */
    public AccountPriceProfile create(AccountPriceProfile profile) {
       // validateUniqueConstraint(profile);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        return repository.save(profile);
    }
    
    /**
     * Update an existing account price profile
     */
    public AccountPriceProfile update(UUID id, AccountPriceProfile profile) {
        AccountPriceProfile existingProfile = findById(id);
        
        // Update fields
        existingProfile.setAccountId(profile.getAccountId());
        existingProfile.setPriceListId(profile.getPriceListId());
        existingProfile.setPriceListCode(profile.getPriceListCode());
        existingProfile.setAccountPlStatus(profile.getAccountPlStatus());
        existingProfile.setStartDate(profile.getStartDate());
        existingProfile.setEndDate(profile.getEndDate());
        existingProfile.setPrioritySequence(profile.getPrioritySequence());
        existingProfile.setExtensionJson(profile.getExtensionJson());
        existingProfile.setUpdatedBy(profile.getUpdatedBy());
        existingProfile.setUpdatedAt(LocalDateTime.now());
        
        return repository.save(existingProfile);
    }
    
    /**
     * Find account price profile by ID
     */
    @Transactional(readOnly = true)
    public AccountPriceProfile findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account price profile not found with id: " + id));
    }
    
    /**
     * Find all account price profiles
     */
    @Transactional(readOnly = true)
    public Page<AccountPriceProfile> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    /**
     * Find all profiles by account ID
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId);
    }
    
    /**
     * Find active profiles for an account
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findActiveProfilesByAccountId(UUID accountId) {
        return repository.findActiveProfilesByAccountId(accountId, LocalDateTime.now());
    }
    
    /**
     * Find profiles by account ID ordered by priority
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findByAccountIdOrderedByPriority(UUID accountId) {
        return repository.findByAccountIdOrderByPrioritySequenceAsc(accountId);
    }
    
    /**
     * Find profiles by price list ID
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findByPriceListId(UUID priceListId) {
        return repository.findByPriceListId(priceListId);
    }
    
    /**
     * Find profiles by price list code
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findByPriceListCode(String priceListCode) {
        return repository.findByPriceListCode(priceListCode);
    }
    
    /**
     * Find profiles by status
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findByStatus(String status) {
        return repository.findByAccountPlStatus(status);
    }
    
    /**
     * Find profiles expiring within date range
     */
    @Transactional(readOnly = true)
    public List<AccountPriceProfile> findExpiringProfiles(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findExpiringProfiles(startDate, endDate);
    }
    
    /**
     * Count active profiles for an account
     */
    @Transactional(readOnly = true)
    public long countActiveProfilesByAccountId(UUID accountId) {
        return repository.countActiveProfilesByAccountId(accountId, LocalDateTime.now());
    }
    
    /**
     * Delete account price profile by ID
     */
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Account price profile not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    /**
     * Check if profile exists
     */
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
    
    /**
     * Activate a profile (set status to ACTIVE)
     */
    public AccountPriceProfile activateProfile(UUID id) {
        AccountPriceProfile profile = findById(id);
        profile.setAccountPlStatus("ACTIVE");
        profile.setUpdatedAt(LocalDateTime.now());
        return repository.save(profile);
    }
    
    /**
     * Deactivate a profile (set status to INACTIVE)
     */
    public AccountPriceProfile deactivateProfile(UUID id) {
        AccountPriceProfile profile = findById(id);
        profile.setAccountPlStatus("INACTIVE");
        profile.setUpdatedAt(LocalDateTime.now());
        return repository.save(profile);
    }
    
    /**
     * Validate unique constraint before saving
     */
    private void validateUniqueConstraint(AccountPriceProfile profile) {
        if (repository.existsByPriceListIdAndStartDateAndAccountPlStatus(
                profile.getPriceListId(), 
                profile.getStartDate(), 
                profile.getAccountPlStatus())) {
            throw new IllegalArgumentException(
                "Account price profile already exists with the same price list ID, start date, and status");
        }
    }
}