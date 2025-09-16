package com.finixone.party.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.finixone.party.model.AccountRelationship;
import com.finixone.party.repository.AccountRelationshipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountRelationshipService {

    // Service methods can be added here as needed
    private final AccountRelationshipRepository accountRelationshipRepository;


    public AccountRelationshipRepository getAccountRelationshipRepository() {
        return accountRelationshipRepository;
    }

   public List<AccountRelationship> getAllAccountRelationships() {
        return accountRelationshipRepository.findAll();
    }   

    public AccountRelationship findById(UUID id) {
        return accountRelationshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountRelationship not found with id: " + id));
    }

    public AccountRelationship createAccountRelationship(AccountRelationship accountRelationship) {
        return accountRelationshipRepository.save(accountRelationship);
    }

    public AccountRelationship updateAccountRelationship(UUID id, AccountRelationship updatedAccountRelationship) {
        AccountRelationship existingAccountRelationship = findById(id);
        
        // Update fields
        existingAccountRelationship.setAccountId(updatedAccountRelationship.getAccountId());
        existingAccountRelationship.setRelatedAccountId(updatedAccountRelationship.getRelatedAccountId());
        existingAccountRelationship.setRelationshipType(updatedAccountRelationship.getRelationshipType());
        existingAccountRelationship.setStartDate(updatedAccountRelationship.getStartDate());
        existingAccountRelationship.setEndDate(updatedAccountRelationship.getEndDate());
        //existingAccountRelationship.setDescription(updatedAccountRelationship.getDescription());
        existingAccountRelationship.setExtensionJson(updatedAccountRelationship.getExtensionJson());
        
        return accountRelationshipRepository.save(existingAccountRelationship);
    }   
    public void deleteAccountRelationship(UUID id) {
        AccountRelationship existingAccountRelationship = findById(id);
        accountRelationshipRepository.delete(existingAccountRelationship);
    }


    public List<AccountRelationship> getAccountRelationshipsByAccountId(UUID accountId) {
        return accountRelationshipRepository.findByAccountId(accountId);
    }   
    public List<AccountRelationship> getAccountRelationshipsByRelatedAccountId(UUID relatedAccountId) {
        return accountRelationshipRepository.findByRelatedAccountId(relatedAccountId);
    }   

    public List<AccountRelationship> getAccountRelationshipsByRelationshipType(String relationshipType) {
        return accountRelationshipRepository.findByRelationshipType(relationshipType);
    }
    public List<AccountRelationship> getAccountRelationshipsByAccountIdAndRelationshipType(UUID accountId, String relationshipType) {
        return accountRelationshipRepository.findByAccountIdAndRelationshipType(accountId, relationshipType);
    }
    public List<AccountRelationship> getAccountRelationshipsByRelatedAccountIdAndRelationshipType(UUID relatedAccountId, String relationshipType) {
        return accountRelationshipRepository.findByRelatedAccountIdAndRelationshipType(relatedAccountId, relationshipType);
    }
    public List<AccountRelationship> getAccountRelationshipsByAccountIdAndRelatedAccountId(UUID accountId, UUID relatedAccountId) {
        return accountRelationshipRepository.findByAccountIdAndRelatedAccountId(accountId, relatedAccountId);
    }
    public List<AccountRelationship> getAccountRelationshipsByAccountIdAndRelatedAccountIdAndRelationshipType(UUID accountId, UUID relatedAccountId, String relationshipType) {
        return accountRelationshipRepository.findByAccountIdAndRelatedAccountIdAndRelationshipType(accountId, relatedAccountId, relationshipType);
    }
    
}
