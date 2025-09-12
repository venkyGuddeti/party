package com.finixone.party.service;

import org.springframework.stereotype.Service;

import com.finixone.party.repository.PartyAddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyAddressService {

    private final PartyAddressRepository partyAddressRepository;

    public com.finixone.party.model.PartyAddress savePartyAddress(com.finixone.party.model.PartyAddress partyAddress) {
        return partyAddressRepository.save(partyAddress);
    }   
    public java.util.List<com.finixone.party.model.PartyAddress> getPartyAddressesByPartyId(java.util.UUID partyId) {
        return partyAddressRepository.findByPartyId(partyId);
    }

    public java.util.List<com.finixone.party.model.PartyAddress> getPartyAddressesByAddressType(String addressType) {
        return partyAddressRepository.findByAddressType(addressType);
    }
    public void deletePartyAddress(java.util.UUID partyAddressId) {
        partyAddressRepository.deleteById(partyAddressId);
    }

    public java.util.List<com.finixone.party.model.PartyAddress> getAllPartyAddresses() {
        return partyAddressRepository.findAll();
    }

    public com.finixone.party.model.PartyAddress updatePartyAddress(com.finixone.party.model.PartyAddress partyAddress) {
        return partyAddressRepository.save(partyAddress);
    }

    public com.finixone.party.model.PartyAddress getPartyAddressById(java.util.UUID partyAddressId) {
        return partyAddressRepository.findById(partyAddressId).orElse(null);
    }
}
