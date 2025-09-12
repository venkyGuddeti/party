package com.finixone.party.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finixone.party.model.PartyIdentifier;


public interface PartyIdentifierRepository extends JpaRepository<com.finixone.party.model.PartyIdentifier, java.util.UUID> {

    List<com.finixone.party.model.PartyIdentifier> findByPartyId(UUID partyId);
    List<PartyIdentifier> findByPartyIdType(String partyIdType);
    

}
