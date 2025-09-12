package com.finixone.party.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.model.Party;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/parties")
@CrossOrigin(origins = "*" )
public class PartyController {

    @Autowired
    private com.finixone.party.service.PartyService partyService;

    public PartyController(com.finixone.party.service.PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping
    public Party saveParty(@RequestBody Party party) {
        return partyService.saveParty(party);
    }
    @PutMapping("/{id}")
    public Party updateParty(@PathVariable("id") UUID partyId, @RequestBody Party party) {
        party.setPartyId(partyId);
        return partyService.updateParty(party);
    }

    @GetMapping("/{id}")
    public Party getPartyById(@PathVariable("id") UUID partyId) {
        return partyService.getPartyById(partyId);
    }

    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable("id") UUID partyId) {
        partyService.deleteParty(partyId);
    }

    @GetMapping
    public java.util.List<Party> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/by-name/{partyName}")
    public java.util.List<Party> getPartiesByPartyName(@PathVariable("partyName") String partyName) {
        return partyService.getPartiesByPartyName(partyName);
    }
 

    @GetMapping("/search")
    public ResponseEntity<Page<Party>>  searchParties (    
        @RequestParam(value = "partyName", required = false) String partyName,
        @RequestParam(value = "partyType", required = false) String partyType,
        @RequestParam(value = "partyStatus", required = false) String partyStatus,
        @RequestParam(value = "primaryIdType", required = false) String primaryIdType,
        @RequestParam(value = "primaryIdNumber", required = false) String primaryIdNumber,
        @RequestParam(value = "segment", required = false) String segment,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "50") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Party> result = partyService.searchParties(
                partyName,
                partyType,
                partyStatus,
                primaryIdType,
                primaryIdNumber,
                segment,
                pageable
        );
        return ResponseEntity.ok(result);
    }
}
