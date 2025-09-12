package com.finixone.party.dto;

import com.finixone.party.model.Party;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyEntity {

    Party party;
    java.util.List<com.finixone.party.model.PartyAddress> partyAddresses;
    java.util.List<com.finixone.party.model.PartyIdentifier> partyIdentifiers;

}
