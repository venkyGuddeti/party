package com.finixone.party.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.service.PartyEntityService;


@RestController
@RequestMapping("/api/party-entities")
@CrossOrigin(origins = "*" )
public class PartyEntityController {

    public PartyEntityService   partyEntityService;
    
    public PartyEntityController(PartyEntityService partyEntityService) {
        this.partyEntityService = partyEntityService;
    }

    @PostMapping
    public com.finixone.party.dto.PartyEntity savePartyEntity(@RequestBody com.finixone.party.dto.PartyEntity partyEntity) {
         return partyEntityService.savePartyEntity(partyEntity);
    }

    @PutMapping("/{id}")
    public com.finixone.party.dto.PartyEntity updatePartyEntity(@PathVariable("id") java.util.UUID partyId, @RequestBody com.finixone.party.dto.PartyEntity partyEntity) {
        return partyEntityService.updatePartyEntity(partyEntity);
    }
    @GetMapping("/{id}")
    public com.finixone.party.dto.PartyEntity getPartyEntityById(@PathVariable("id") java.util.UUID partyId) {
        return partyEntityService.getPartyEntityById(partyId);
    }   
}
