package com.finixone.party.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finixone.party.model.Party;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyService {

    @Autowired
    private final com.finixone.party.repository.PartyRepository partyRepository;

    public org.springframework.data.domain.Page<com.finixone.party.model.Party> searchParties(    
        String partyName,
        String partyType,
        String partyStatus,
        String primaryIdType,
        String primaryIdNumber,
        String segment,
        org.springframework.data.domain.Pageable pageable
    ) {
        return partyRepository.findParties(
            partyName != null ? partyName.toUpperCase() : null,
            partyType,
            partyStatus,
            primaryIdType,
            primaryIdNumber,
            segment,
            pageable
        );
    }

    public Party saveParty(Party party) {
        return partyRepository.save(party);
    }

    public Party updateParty(Party party) {
        return partyRepository.save(party);
    }   

    public Party getPartyById(java.util.UUID partyId) {
        return partyRepository.findById(partyId).orElse(null);
    }

    public void deleteParty(java.util.UUID partyId) {
        partyRepository.deleteById(partyId);
    }   

    public java.util.List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    public java.util.List<Party> getPartiesByPartyName(String partyName) {
        return partyRepository.findByPartyName(partyName);
    }

    public java.util.List<Party> getPartiesByEmail(String email) {
        return partyRepository.findByEmail(email);
    }   
    public java.util.List<Party> getPartiesByPrimaryIdType(String primaryIdType) {
        return partyRepository.findByPrimaryIdType(primaryIdType);
    }   

    public long countParties() {
        return partyRepository.count();
    }


}
