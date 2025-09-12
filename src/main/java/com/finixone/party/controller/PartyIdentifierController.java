package com.finixone.party.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.model.PartyIdentifier;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/party-identifiers")
@CrossOrigin(origins = "*" )
public class PartyIdentifierController {

    @Autowired
    private com.finixone.party.service.PartyIdentifierService partyIdentifierService;

    public PartyIdentifierController(com.finixone.party.service.PartyIdentifierService partyIdentifierService) {
        this.partyIdentifierService = partyIdentifierService;
    }

   @PostMapping
    public com.finixone.party.model.PartyIdentifier savePartyIdentifier(@RequestBody com.finixone.party.model.PartyIdentifier partyIdentifier) {
         return partyIdentifierService.savePartyIdentifier(partyIdentifier);
    }

    @PutMapping("path/{id}")
    public PartyIdentifier updatePartyIdentifier(@PathVariable("id") java.util.UUID partyIdentifierId, @RequestBody PartyIdentifier partyIdentifier) {
        partyIdentifier.setPartyIdentifierId(partyIdentifierId);
        return partyIdentifierService.updatePartyIdentifier(partyIdentifier);
    }

    @DeleteMapping("/{id}")
    public void deletePartyIdentifier(@PathVariable("id") java.util.UUID partyIdentifierId) {
        partyIdentifierService.deletePartyIdentifier(partyIdentifierId);
    }

    @GetMapping
    public java.util.List<PartyIdentifier> getAllPartyIdentifiers() {
        return partyIdentifierService.getAllPartyIdentifiers();
    }
   

}
