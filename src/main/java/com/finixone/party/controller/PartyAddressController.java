package com.finixone.party.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.service.PartyAddressService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;



@RestController
@RequestMapping("/api/party-addresses")
@CrossOrigin(origins = "*" )
public class PartyAddressController {

    private final PartyAddressService partyAddressService;

    public PartyAddressController(PartyAddressService partyAddressService) {
        this.partyAddressService = partyAddressService;
    }

    @PostMapping
    public com.finixone.party.model.PartyAddress savePartyAddress(@RequestBody com.finixone.party.model.PartyAddress partyAddress) {
         return partyAddressService.savePartyAddress(partyAddress);
    }

    @PutMapping("path/{id}")
    public com.finixone.party.model.PartyAddress updatePartyAddress(@PathVariable("id") java.util.UUID partyAddressId, @RequestBody com.finixone.party.model.PartyAddress partyAddress) {
        partyAddress.setPartyAddressId(partyAddressId);
        return partyAddressService.updatePartyAddress(partyAddress);
    }

    @DeleteMapping("/{id}")
    public void deletePartyAddress(@PathVariable("id") java.util.UUID partyAddressId) {
        partyAddressService.deletePartyAddress(partyAddressId);
    }   
    @GetMapping
    public java.util.List<com.finixone.party.model.PartyAddress> getAllPartyAddresses() {
        return partyAddressService.getAllPartyAddresses();
    }

}
