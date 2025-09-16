package com.finixone.party.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finixone.party.model.AccountRelationship;

public interface AccountRelationshipRepository extends JpaRepository<AccountRelationship, UUID> {
    // Additional query methods can be defined here if needed
    List<AccountRelationship> findByAccountId(UUID accountId);
    List<AccountRelationship> findByRelatedAccountId(UUID relatedAccountId);
    List<AccountRelationship> findByRelationshipType(String relationshipType);
    List<AccountRelationship> findByAccountIdAndRelationshipType(UUID accountId, String relationshipType);
    List<AccountRelationship> findByRelatedAccountIdAndRelationshipType(UUID relatedAccountId, String relationshipType);
    List<AccountRelationship> findByAccountIdAndRelatedAccountId(UUID accountId, UUID relatedAccountId);
    List<AccountRelationship> findByAccountIdAndRelatedAccountIdAndRelationshipType(UUID accountId, UUID relatedAccountId, String relationshipType);
    
    

}
