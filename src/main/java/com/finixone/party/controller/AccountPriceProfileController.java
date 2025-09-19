package com.finixone.party.controller;



import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.finixone.party.model.AccountPriceProfile;
import com.finixone.party.service.AccountPriceProfileService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account-price-profiles")
@CrossOrigin(origins = "*")
public class AccountPriceProfileController {
    
    private final AccountPriceProfileService service;
    
    @Autowired
    public AccountPriceProfileController(AccountPriceProfileService service) {
        this.service = service;
    }
    
    /**
     * Create a new account price profile
     */
    @PostMapping
    public ResponseEntity<AccountPriceProfile> create(@Valid @RequestBody AccountPriceProfile profile) {
        try {
            AccountPriceProfile createdProfile = service.create(profile);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get account price profile by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountPriceProfile> getById(@PathVariable UUID id) {
        try {
            AccountPriceProfile profile = service.findById(id);
            return ResponseEntity.ok(profile);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get all account price profiles with pagination
     */
    @GetMapping
    public ResponseEntity<Page<AccountPriceProfile>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : 
                   Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AccountPriceProfile> profiles = service.findAll(pageable);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Update account price profile
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountPriceProfile> update(@PathVariable UUID id, 
                                                     @Valid @RequestBody AccountPriceProfile profile) {
        try {
            AccountPriceProfile updatedProfile = service.update(id, profile);
            return ResponseEntity.ok(updatedProfile);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Delete account price profile
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get profiles by account ID
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AccountPriceProfile>> getByAccountId(@PathVariable UUID accountId) {
        List<AccountPriceProfile> profiles = service.findByAccountId(accountId);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get active profiles by account ID
     */
    @GetMapping("/account/{accountId}/active")
    public ResponseEntity<List<AccountPriceProfile>> getActiveByAccountId(@PathVariable UUID accountId) {
        List<AccountPriceProfile> profiles = service.findActiveProfilesByAccountId(accountId);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get profiles by account ID ordered by priority
     */
    @GetMapping("/account/{accountId}/priority-ordered")
    public ResponseEntity<List<AccountPriceProfile>> getByAccountIdOrderedByPriority(@PathVariable UUID accountId) {
        List<AccountPriceProfile> profiles = service.findByAccountIdOrderedByPriority(accountId);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get profiles by price list ID
     */
    @GetMapping("/price-list/{priceListId}")
    public ResponseEntity<List<AccountPriceProfile>> getByPriceListId(@PathVariable UUID priceListId) {
        List<AccountPriceProfile> profiles = service.findByPriceListId(priceListId);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get profiles by price list code
     */
    @GetMapping("/price-list-code/{priceListCode}")
    public ResponseEntity<List<AccountPriceProfile>> getByPriceListCode(@PathVariable String priceListCode) {
        List<AccountPriceProfile> profiles = service.findByPriceListCode(priceListCode);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get profiles by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AccountPriceProfile>> getByStatus(@PathVariable String status) {
        List<AccountPriceProfile> profiles = service.findByStatus(status);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Get profiles expiring within date range
     */
    @GetMapping("/expiring")
    public ResponseEntity<List<AccountPriceProfile>> getExpiringProfiles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AccountPriceProfile> profiles = service.findExpiringProfiles(startDate, endDate);
        return ResponseEntity.ok(profiles);
    }
    
    /**
     * Count active profiles for an account
     */
    @GetMapping("/account/{accountId}/count-active")
    public ResponseEntity<Long> countActiveByAccountId(@PathVariable UUID accountId) {
        long count = service.countActiveProfilesByAccountId(accountId);
        return ResponseEntity.ok(count);
    }
    
    /**
     * Activate a profile
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<AccountPriceProfile> activate(@PathVariable UUID id) {
        try {
            AccountPriceProfile profile = service.activateProfile(id);
            return ResponseEntity.ok(profile);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Deactivate a profile
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AccountPriceProfile> deactivate(@PathVariable UUID id) {
        try {
            AccountPriceProfile profile = service.deactivateProfile(id);
            return ResponseEntity.ok(profile);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Check if profile exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable UUID id) {
        boolean exists = service.existsById(id);
        return ResponseEntity.ok(exists);
    }
}