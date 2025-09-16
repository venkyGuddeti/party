package com.finixone.party.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finixone.party.model.AccountContact;
import com.finixone.party.service.AccountContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account-contacts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountContactController {

    private final AccountContactService accountContactService;

    @Autowired
    public AccountContactController(AccountContactService accountContactService) {
        this.accountContactService = accountContactService;
    }

    /**
     * Create a new account contact
     */
    @PostMapping
    public ResponseEntity<?> createContact(@Valid @RequestBody AccountContact contact) {
        try {
            AccountContact createdContact = accountContactService.createContact(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create contact: " + e.getMessage()));
        }
    }

    /**
     * Get contact by ID
     */
    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContactById(@PathVariable UUID contactId) {
        try {
            Optional<AccountContact> contact = accountContactService.findContactByIdOptional(contactId);
            if (contact.isPresent()) {
                return ResponseEntity.ok(contact.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve contact: " + e.getMessage()));
        }
    }

    /**
     * Update an existing contact
     */
    @PutMapping("/{contactId}")
    public ResponseEntity<?> updateContact(@PathVariable UUID contactId, 
                                         @Valid @RequestBody AccountContact contactDetails) {
        try {
            AccountContact updatedContact = accountContactService.updateContact(contactId, contactDetails);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update contact: " + e.getMessage()));
        }
    }

    /**
     * Get all contacts with pagination
     */
    @GetMapping
    public ResponseEntity<?> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                       Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<AccountContact> contacts = accountContactService.findAllContacts(pageable);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve contacts: " + e.getMessage()));
        }
    }

    /**
     * Get contacts by account ID
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getContactsByAccountId(@PathVariable UUID accountId) {
        try {
            List<AccountContact> contacts = accountContactService.findContactsByAccountId(accountId);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve contacts: " + e.getMessage()));
        }
    }


}
