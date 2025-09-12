package com.finixone.party.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finixone.party.dto.PartyEntity;
import com.finixone.party.model.Party;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyEntityService {

    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyIdentifierService partyIdentifierService;
    @Autowired
    private PartyAddressService partyAddressService;

    public PartyEntity savePartyEntity(PartyEntity partyEntity) {

        if (partyEntity.getParty() != null) {
            partyEntity.getParty().setPartyNameUpper(partyEntity.getParty().getPartyName().toUpperCase());
            Party savedParty = partyService.saveParty(partyEntity.getParty());
            partyEntity.setParty(savedParty);
        }
        if (partyEntity.getPartyIdentifiers() != null) {
            for (com.finixone.party.model.PartyIdentifier identifier : partyEntity.getPartyIdentifiers()) {
                identifier.setPartyId(partyEntity.getParty().getPartyId());
                partyIdentifierService.savePartyIdentifier(identifier);
            }
        }
        if (partyEntity.getPartyAddresses() != null) {
            for (com.finixone.party.model.PartyAddress address : partyEntity.getPartyAddresses()) {
                address.setPartyId(partyEntity.getParty().getPartyId());
                partyAddressService.savePartyAddress(address);
            }
        }
        return partyEntity;
    }

    public PartyEntity getPartyEntityById(java.util.UUID partyId) {
        PartyEntity partyEntity = new PartyEntity();
        partyEntity.setParty(partyService.getPartyById(partyId));
        partyEntity.setPartyIdentifiers(partyIdentifierService.getPartyIdentifiersByPartyId(partyId));
        partyEntity.setPartyAddresses(partyAddressService.getPartyAddressesByPartyId(partyId));
        return partyEntity;
    }

    public PartyEntity updatePartyEntity(PartyEntity partyEntity) {
        if (partyEntity.getParty() != null) {
            partyEntity.getParty().setPartyNameUpper(partyEntity.getParty().getPartyName().toUpperCase());
            partyService.updateParty(partyEntity.getParty());
        }
        if (partyEntity.getPartyIdentifiers() != null) {
            for (com.finixone.party.model.PartyIdentifier identifier : partyEntity.getPartyIdentifiers()) {
                identifier.setPartyId(null);
                partyIdentifierService.updatePartyIdentifier(identifier);
            }
        }
        if (partyEntity.getPartyAddresses() != null) {
            for (com.finixone.party.model.PartyAddress address : partyEntity.getPartyAddresses()) {
                partyAddressService.updatePartyAddress(address);
            }
        }
        return partyEntity;
    }
    

}
