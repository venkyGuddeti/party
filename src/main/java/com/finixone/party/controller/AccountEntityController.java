package com.finixone.party.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.dto.AccountEntity;
import com.finixone.party.service.AccountEntityService;

@RestController
@RequestMapping("/api/account-entities")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountEntityController {

    AccountEntityService accountEntityService;

    public AccountEntityController(AccountEntityService accountEntityService) {
        this.accountEntityService = accountEntityService;
    }

    public AccountEntityService getAccountEntityService() {
        return accountEntityService;
    }

    @GetMapping("/{accountId}")
    public AccountEntity getAccountEntityById(@PathVariable UUID accountId) {
        return accountEntityService.getAccountEntityById(accountId);
    }

    @PostMapping
    public AccountEntity createAccountEntity(@RequestBody AccountEntity accountEntity) {
        return accountEntityService.saveAccountEntity(accountEntity);
    }

    @GetMapping("/party/{partyId}")
    public List<AccountEntity> getAccountEntitiesByPartyId(@PathVariable UUID partyId) {
        return accountEntityService.getAccountEntityByPartyId(partyId);
    }

}
