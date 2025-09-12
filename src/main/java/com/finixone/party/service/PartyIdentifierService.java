package com.finixone.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyIdentifierService {

    @Autowired
    private final com.finixone.party.repository.PartyIdentifierRepository partyIdentifierRepository;

    public com.finixone.party.model.PartyIdentifier savePartyIdentifier(com.finixone.party.model.PartyIdentifier partyIdentifier) {
        return partyIdentifierRepository.save(partyIdentifier);
    }
    public List<com.finixone.party.model.PartyIdentifier> getPartyIdentifiersByPartyId(java.util.UUID partyId) {
        return partyIdentifierRepository.findByPartyId(partyId);
    }

    public java.util.List<com.finixone.party.model.PartyIdentifier> getPartyIdentifiersByPartyIdType(String partyIdType) {
        return partyIdentifierRepository.findByPartyIdType(partyIdType);
    }

    public void deletePartyIdentifier(java.util.UUID partyIdentifierId) {
        partyIdentifierRepository.deleteById(partyIdentifierId);
    }

    public java.util.List<com.finixone.party.model.PartyIdentifier> getAllPartyIdentifiers() {
        return partyIdentifierRepository.findAll();
    }

    public com.finixone.party.model.PartyIdentifier updatePartyIdentifier(com.finixone.party.model.PartyIdentifier partyIdentifier) {
        return partyIdentifierRepository.save(partyIdentifier);
    }

}
