package com.finixone.party.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.model.Account;
import com.finixone.party.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    private final AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    /**
     * Create a new account
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get all accounts with pagination
     */
    @GetMapping
    public ResponseEntity<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Account> accounts = accountService.getAllAccounts(pageable);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get account by ID
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get account by account number
     */
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        try {
            Optional<Account> account = accountService.getAccountByAccountNumber(accountNumber);
            if (account.isPresent()) {
                return new ResponseEntity<>(account.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Update account
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID accountId, 
                                               @Valid @RequestBody Account account) {
        try {
            Account updatedAccount = accountService.updateAccount(accountId, account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Delete account
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable UUID accountId) {
        try {
            accountService.deleteAccount(accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Soft delete account
     */
    @PatchMapping("/{accountId}/soft-delete")
    public ResponseEntity<Account> softDeleteAccount(@PathVariable UUID accountId) {
        try {
            Account account = accountService.softDeleteAccount(accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get accounts by party ID
     */
    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<Account>> getAccountsByPartyId(@PathVariable UUID partyId) {
        try {
            List<Account> accounts = accountService.getAccountsByPartyId(partyId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Account>> searchAccountsByCriteria(
            @RequestParam(value = "partyId", required = false) UUID partyId,
            @RequestParam(value = "partyName", required = false) String partyName,
            @RequestParam(value = "partyType", required = false) String partyType,
            @RequestParam(value = "accountNumber", required = false) String accountNumber,
            @RequestParam(value = "accountStatus", required = false) String accountStatus,
            @RequestParam(value = "accountType", required = false) String accountType,
            @RequestParam(value = "companyCode", required = false) String companyCode,
            @RequestParam(value = "businessUnit", required = false) String businessUnit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size ,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
            ) {
        try {
            System.out.println("Search parameters: partyId=" + partyId + ", partyName=" + partyName + 
                               ", partyType=" + partyType + ", accountNumber=" + accountNumber +
                               ", accountStatus=" + accountStatus + ", accountType=" + accountType +
                               ", companyCode=" + companyCode + ", businessUnit=" + businessUnit);
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Account> accounts = accountService.searchAccountsByCriteria(
                partyId, partyName, partyType, accountNumber, accountStatus, accountType, companyCode, businessUnit, pageable);
            return new ResponseEntity<>(accounts, HttpStatus.OK);   
        } catch (Exception e) { 
            System.out.println("Error during search: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
