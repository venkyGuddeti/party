package com.finixone.party.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finixone.party.model.Account;
import com.finixone.party.repository.AccountRepository;
import com.finixone.party.utils.GenericUtils;

@Service
@Transactional
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    /**
     * Create a new account
     */
    public Account createAccount(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    /**
     * Update an existing account
     */
    public Account updateAccount(UUID accountId, Account accountDetails) {
        Account account = getAccountById(accountId);
        
        // Update fields
        account.setPartyId(accountDetails.getPartyId());
        account.setAccountStatus(accountDetails.getAccountStatus());
        account.setAccountType(accountDetails.getAccountType());
        account.setAccountIdType(accountDetails.getAccountIdType());
        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setAccountName(accountDetails.getAccountName());
        account.setCurrency(accountDetails.getCurrency());
        account.setStartDate(accountDetails.getStartDate());
        account.setEndDate(accountDetails.getEndDate());
        account.setTaxExempted(accountDetails.getTaxExempted());
        account.setAllowMultiCurrency(accountDetails.getAllowMultiCurrency());
      //  account.setAutoCollectionSw(accountDetails.getAutoCollectionSw());
        account.setCompanyCode(accountDetails.getCompanyCode());
        account.setBusinessUnit(accountDetails.getBusinessUnit());
        account.setOperatingUnit(accountDetails.getOperatingUnit());
        account.setCostCenter(accountDetails.getCostCenter());
        account.setSegmentCode(accountDetails.getSegmentCode());
        account.setAccessGroup(accountDetails.getAccessGroup());
        account.setUserResponsible(accountDetails.getUserResponsible());
        account.setBillCycleDay(accountDetails.getBillCycleDay());
        account.setOverridePayTerms(accountDetails.getOverridePayTerms());
        account.setBillHold(accountDetails.getBillHold());
        account.setBillHoldDate(accountDetails.getBillHoldDate());
        account.setCollectionHold(accountDetails.getCollectionHold());
        account.setActivityTz(accountDetails.getActivityTz());
        account.setExtensionJson(accountDetails.getExtensionJson());
        account.setUpdatedAt(LocalDateTime.now());
        account.setUpdatedBy(accountDetails.getUpdatedBy());
        
        return accountRepository.save(account);
    }
    
    /**
     * Get account by ID
     */
    @Transactional(readOnly = true)
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }
    
    /**
     * Get account by account number
     */
    @Transactional(readOnly = true)
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    /**
     * Get all accounts with pagination
     */
    @Transactional(readOnly = true)
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
    
    /**
     * Get accounts by party ID
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByPartyId(UUID partyId) {
        return accountRepository.findByPartyId(partyId);
    }
    
    /**
     * Get accounts by status
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByStatus(String accountStatus) {
        return accountRepository.findByAccountStatus(accountStatus);
    }
    
    /**
     * Get accounts by type
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByType(String accountType) {
        return accountRepository.findByAccountType(accountType);
    }
    
    /**
     * Get accounts by company code
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByCompanyCode(String companyCode) {
        return accountRepository.findByCompanyCode(companyCode);
    }
    
    /**
     * Get accounts by business unit
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByBusinessUnit(String businessUnit) {
        return accountRepository.findByBusinessUnit(businessUnit);
    }
    
    /**
     * Get accounts by user responsible
     */
    @Transactional(readOnly = true)
    public List<Account> getAccountsByUserResponsible(UUID userResponsible) {
        return accountRepository.findByUserResponsible(userResponsible);
    }
    
    /**
     * Get active accounts
     */
    @Transactional(readOnly = true)
    public List<Account> getActiveAccounts() {
        return accountRepository.findActiveAccounts(LocalDateTime.now());
    }
    
    /**
     * Get expired accounts
     */
    @Transactional(readOnly = true)
    public List<Account> getExpiredAccounts() {
        return accountRepository.findExpiredAccounts(LocalDateTime.now());
    }
    
    /**
     * Search accounts by criteria with pagination
     */
    @Transactional(readOnly = true)
    public Page<Account> searchAccountsByCriteria(
            UUID partyId,
            String partyName,
            String partyType,
            String accountNumber,
            String accountStatus,
            String accountType,
            String companyCode,
            String businessUnit,
            Pageable pageable) {
        
        return accountRepository.findAccountsByCriteria(
                partyId, GenericUtils.isNullOrEmpty(partyName)?"":partyName.toUpperCase(), partyType, accountNumber, accountStatus, accountType, companyCode, businessUnit, pageable);
    }
    
    /**
     * Delete account by ID
     */
    public void deleteAccount(UUID accountId) {
        Account account = getAccountById(accountId);
        accountRepository.delete(account);
    }
    
    /**
     * Soft delete - set end date to current timestamp
     */
    public Account softDeleteAccount(UUID accountId) {
        Account account = getAccountById(accountId);
        account.setEndDate(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    /**
     * Check if account number exists
     */
    @Transactional(readOnly = true)
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }
    
    /**
     * Count accounts by status
     */
    @Transactional(readOnly = true)
    public Long countAccountsByStatus(String accountStatus) {
        return accountRepository.countByAccountStatus(accountStatus);
    }
    
    /**
     * Count accounts by party
     */
    @Transactional(readOnly = true)
    public Long countAccountsByParty(UUID partyId) {
        return accountRepository.countByPartyId(partyId);
    }
    
    /**
     * Activate account
     */
    public Account activateAccount(UUID accountId) {
        Account account = getAccountById(accountId);
        account.setAccountStatus("ACTIVE");
        account.setEndDate(null);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    /**
     * Deactivate account
     */
    public Account deactivateAccount(UUID accountId) {
        Account account = getAccountById(accountId);
        account.setAccountStatus("INACTIVE");
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    /**
     * Toggle bill hold
     */
    public Account toggleBillHold(UUID accountId, boolean billHold) {
        Account account = getAccountById(accountId);
        account.setBillHold(billHold);
        account.setBillHoldDate(billHold ? LocalDateTime.now().toLocalDate() : null);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
    
    /**
     * Toggle collection hold
     */
    public Account toggleCollectionHold(UUID accountId, boolean collectionHold) {
        Account account = getAccountById(accountId);
        account.setCollectionHold(collectionHold);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
}
