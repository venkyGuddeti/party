package com.finixone.party.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finixone.party.model.AccountContact;
import com.finixone.party.repository.AccountContactRepository;

@Service
@Transactional
public class AccountContactService {

    private final AccountContactRepository accountContactRepository;

    @Autowired
    public AccountContactService(AccountContactRepository accountContactRepository) {
        this.accountContactRepository = accountContactRepository;
    }

    /**
     * Create a new account contact
     */
    public AccountContact createContact(AccountContact contact) {
        validateRequiredFields(contact);
        
        // Check for duplicate email if provided
        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            if (accountContactRepository.findByEmail(contact.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Contact with email already exists: " + contact.getEmail());
            }
        }
        
        return accountContactRepository.save(contact);
    }

    /**
     * Update an existing account contact
     */
    public AccountContact updateContact(UUID contactId, AccountContact contactDetails) {
        AccountContact existingContact = findContactById(contactId);
        
        // Check for duplicate email if email is being changed
        if (contactDetails.getEmail() != null && !contactDetails.getEmail().equals(existingContact.getEmail())) {
            if (accountContactRepository.existsByEmailAndContactIdNot(contactDetails.getEmail(), contactId)) {
                throw new IllegalArgumentException("Contact with email already exists: " + contactDetails.getEmail());
            }
        }
        
        updateContactFields(existingContact, contactDetails);
        return accountContactRepository.save(existingContact);
    }

    /**
     * Find contact by ID
     */
    @Transactional(readOnly = true)
    public AccountContact findContactById(UUID contactId) {
        return accountContactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + contactId));
    }

    /**
     * Find contact by ID (Optional)
     */
    @Transactional(readOnly = true)
    public Optional<AccountContact> findContactByIdOptional(UUID contactId) {
        return accountContactRepository.findById(contactId);
    }

    /**
     * Find all contacts by account ID
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findContactsByAccountId(UUID accountId) {
        return accountContactRepository.findByAccountId(accountId);
    }

    /**
     * Find contacts by account ID with pagination
     */
    @Transactional(readOnly = true)
    public Page<AccountContact> findContactsByAccountId(UUID accountId, Pageable pageable) {
        return accountContactRepository.findByAccountId(accountId, pageable);
    }

    /**
     * Find active contacts by account ID
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findActiveContactsByAccountId(UUID accountId) {
        return accountContactRepository.findActiveContactsByAccountId(accountId);
    }

    /**
     * Find contacts by account ID and contact type
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findContactsByAccountIdAndType(UUID accountId, String contactType) {
        return accountContactRepository.findByAccountIdAndContactType(accountId, contactType);
    }

    /**
     * Find contact by email
     */
    @Transactional(readOnly = true)
    public Optional<AccountContact> findContactByEmail(String email) {
        return accountContactRepository.findByEmail(email);
    }

    /**
     * Find contacts by entity name
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findContactsByEntityName(String entityName) {
        return accountContactRepository.findByEntityNameContainingIgnoreCase(entityName);
    }

    /**
     * Find contacts by status
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findContactsByStatus(String status) {
        return accountContactRepository.findByContactStatus(status);
    }

    /**
     * Find all contacts with pagination
     */
    @Transactional(readOnly = true)
    public Page<AccountContact> findAllContacts(Pageable pageable) {
        return accountContactRepository.findAll(pageable);
    }

    /**
     * Search contacts with multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<AccountContact> searchContacts(UUID accountId, String contactType, String contactStatus,
                                             String entityName, String email, String city, 
                                             String state, String country, Pageable pageable) {
        return accountContactRepository.findContactsWithCriteria(
                accountId, contactType, contactStatus, entityName, 
                email, city, state, country, pageable);
    }

    /**
     * Count contacts by account ID
     */
    @Transactional(readOnly = true)
    public long countContactsByAccountId(UUID accountId) {
        return accountContactRepository.countByAccountId(accountId);
    }

    /**
     * Count active contacts by account ID
     */
    @Transactional(readOnly = true)
    public long countActiveContactsByAccountId(UUID accountId) {
        return accountContactRepository.countActiveContactsByAccountId(accountId);
    }

    /**
     * Find contacts expiring within specified days
     */
    @Transactional(readOnly = true)
    public List<AccountContact> findContactsExpiringWithinDays(int days) {
        LocalDate expiryDate = LocalDate.now().plusDays(days);
        return accountContactRepository.findContactsExpiringBefore(expiryDate);
    }

    /**
     * Update contact status
     */
    public boolean updateContactStatus(UUID contactId, String status, String updatedBy) {
        int updatedRows = accountContactRepository.updateContactStatus(contactId, status, updatedBy);
        return updatedRows > 0;
    }

    /**
     * Soft delete contact (set end date)
     */
    public boolean softDeleteContact(UUID contactId, String updatedBy) {
        int updatedRows = accountContactRepository.softDeleteContact(contactId, updatedBy);
        return updatedRows > 0;
    }

    /**
     * Hard delete contact
     */
    public void deleteContact(UUID contactId) {
        if (!accountContactRepository.existsById(contactId)) {
            throw new RuntimeException("Contact not found with ID: " + contactId);
        }
        accountContactRepository.deleteById(contactId);
    }

    /**
     * Check if contact exists
     */
    @Transactional(readOnly = true)
    public boolean contactExists(UUID contactId) {
        return accountContactRepository.existsById(contactId);
    }

    /**
     * Validate required fields
     */
    private void validateRequiredFields(AccountContact contact) {
        if (contact.getAccountId() == null) {
            throw new IllegalArgumentException("Account ID is required");
        }
        if (contact.getContactType() == null || contact.getContactType().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact type is required");
        }
        if (contact.getEntityName() == null || contact.getEntityName().trim().isEmpty()) {
            throw new IllegalArgumentException("Entity name is required");
        }
        if (contact.getContactStatus() == null || contact.getContactStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact status is required");
        }
        if (contact.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
    }

    /**
     * Update contact fields from details
     */
    private void updateContactFields(AccountContact existingContact, AccountContact contactDetails) {
        if (contactDetails.getAccountId() != null) {
            existingContact.setAccountId(contactDetails.getAccountId());
        }
        if (contactDetails.getContactType() != null) {
            existingContact.setContactType(contactDetails.getContactType());
        }
        if (contactDetails.getEntityName() != null) {
            existingContact.setEntityName(contactDetails.getEntityName());
        }
        if (contactDetails.getEmail() != null) {
            existingContact.setEmail(contactDetails.getEmail());
        }
        if (contactDetails.getContactStatus() != null) {
            existingContact.setContactStatus(contactDetails.getContactStatus());
        }
        if (contactDetails.getAddressLine1() != null) {
            existingContact.setAddressLine1(contactDetails.getAddressLine1());
        }
        if (contactDetails.getAddressLine2() != null) {
            existingContact.setAddressLine2(contactDetails.getAddressLine2());
        }
        if (contactDetails.getAddressLine3() != null) {
            existingContact.setAddressLine3(contactDetails.getAddressLine3());
        }
        if (contactDetails.getAddressLine4() != null) {
            existingContact.setAddressLine4(contactDetails.getAddressLine4());
        }
        if (contactDetails.getCity() != null) {
            existingContact.setCity(contactDetails.getCity());
        }
        if (contactDetails.getState() != null) {
            existingContact.setState(contactDetails.getState());
        }
        if (contactDetails.getCounty() != null) {
            existingContact.setCounty(contactDetails.getCounty());
        }
        if (contactDetails.getCountry() != null) {
            existingContact.setCountry(contactDetails.getCountry());
        }
        if (contactDetails.getPostalCode() != null) {
            existingContact.setPostalCode(contactDetails.getPostalCode());
        }
        if (contactDetails.getStartDate() != null) {
            existingContact.setStartDate(contactDetails.getStartDate());
        }
        if (contactDetails.getEndDate() != null) {
            existingContact.setEndDate(contactDetails.getEndDate());
        }
        if (contactDetails.getExtensionJson() != null) {
            existingContact.setExtensionJson(contactDetails.getExtensionJson());
        }
        if (contactDetails.getUpdatedBy() != null) {
            existingContact.setUpdatedBy(contactDetails.getUpdatedBy());
        }
    }
}