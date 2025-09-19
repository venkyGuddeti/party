package com.finixone.party.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finixone.party.dto.AccountContactDto;
import com.finixone.party.dto.AccountDto;
import com.finixone.party.dto.AccountEntity;
import com.finixone.party.dto.AccountPriceProfileDto;
import com.finixone.party.mapper.AccountContactMapper;
import com.finixone.party.mapper.AccountMapper;
import com.finixone.party.mapper.AccountPriceProfileMapper;
import com.finixone.party.model.Account;
import com.finixone.party.model.AccountContact;
import com.finixone.party.model.AccountPriceProfile;
import com.finixone.party.utils.GenericUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountEntityService {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final AccountContactService accountContactService;
    @Autowired
    private final AccountRelationshipService accountRelationshipService;
    @Autowired
    private final AccountPriceProfileService accountPriceProfileService;

    @Transactional
    public AccountEntity saveAccountEntity(com.finixone.party.dto.AccountEntity accountEntity) {

        AccountDto accountDto = accountEntity.getAccount();
        if (GenericUtils.isCreateOperation(accountDto.getOperation())) {
            Account account = AccountMapper.INSTANCE.toEntity(accountDto);
            account = accountService.createAccount(account);
            accountDto = AccountMapper.INSTANCE.toDto(account);
            accountEntity.setAccount(accountDto);

            List<AccountContactDto> contactDtos = accountEntity.getAccountContacts();
            if (contactDtos != null && !contactDtos.isEmpty()) {
                for (AccountContactDto contactDto : contactDtos) {
                    contactDto.setAccountId(account.getAccountId());
                    AccountContact accountContact = AccountContactMapper.INSTANCE.toEntity(contactDto);
                    if(accountContact.getContactId() == null) {
                        accountContact.setAccountId(account.getAccountId());
                        accountContactService.createContact(accountContact);
                    }else{
                        accountContactService.updateContact(accountContact.getContactId(), accountContact);
                    }
                }
            }

            List<AccountPriceProfileDto> profileDtos = accountEntity.getPriceProfiles();
            if (profileDtos != null && !profileDtos.isEmpty()) {
                // iterate a copy and collect created profiles into a new list to avoid
                // ConcurrentModificationException when modifying the original list while iterating
                List<AccountPriceProfileDto> newProfiles = new ArrayList<>();
                for (AccountPriceProfileDto profileDto : new ArrayList<>(profileDtos)) {
                    profileDto.setAccountId(account.getAccountId());
                    AccountPriceProfile accountPriceProfile = AccountPriceProfileMapper.INSTANCE.toEntity(profileDto);
                    if(accountPriceProfile.getAccountPriceProfileId() == null) {
                        accountPriceProfile.setAccountId(account.getAccountId());
                        accountPriceProfileService.create(accountPriceProfile);
                    }else{
                        accountPriceProfileService.update(accountPriceProfile.getAccountPriceProfileId(), accountPriceProfile);
                    }
                    AccountPriceProfileDto profileDtoNew = AccountPriceProfileMapper.INSTANCE.toDto(accountPriceProfile);
                    newProfiles.add(profileDtoNew);
                }
                // replace the original list with the list of created profiles (or use addAll to append)
                accountEntity.setPriceProfiles(newProfiles);
            }
        }
        return accountEntity;
    }

    public AccountEntity getAccountEntityById(UUID accountId) {
        Account account = accountService.getAccountById(accountId);
        AccountDto accountDto = AccountMapper.INSTANCE.toDto(account);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(accountDto);
      //  accountEntity.setParty(partyDto);

       List<AccountContact> accountContacts = accountContactService.findActiveContactsByAccountId(accountId);
            List<AccountContactDto> contactDtos = new ArrayList<>();
            for (AccountContact contact : accountContacts) {
                AccountContactDto contactDto = new AccountContactDto();
                contactDto = AccountContactMapper.INSTANCE.toDto(contact);
                contactDtos.add(contactDto);
            }
       accountEntity.setAccountContacts(contactDtos);
        List<AccountPriceProfile> accountPriceProfiles = accountPriceProfileService
                .findActiveProfilesByAccountId(accountId);
        if (accountPriceProfiles != null) {
            accountEntity.setPriceProfiles(new ArrayList<>());
            accountPriceProfiles.forEach(accountPriceProfile -> {
                AccountPriceProfileDto profileDto = AccountPriceProfileMapper.INSTANCE.toDto(accountPriceProfile);
                accountEntity.getPriceProfiles().add(profileDto);
            });
        }
        return accountEntity;
    }

    public List<AccountEntity> getAccountEntityByPartyId(UUID partyId) {
        List<Account> accounts = accountService.getAccountsByPartyId(partyId);
        List<AccountEntity> accountEntities = new ArrayList<>();
        for (Account account : accounts) {
            AccountDto accountDto = AccountMapper.INSTANCE.toDto(account);
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccount(accountDto);
          //  accountEntity.setParty(partyDto);
              List<AccountContact> accountContacts = accountContactService.findActiveContactsByAccountId(account.getAccountId());
              List<AccountContactDto> contactDtos = new ArrayList<>();
              for (AccountContact contact : accountContacts) {
                  AccountContactDto contactDto = AccountContactMapper.INSTANCE.toDto(contact);
                  contactDtos.add(contactDto);
              }
              accountEntity.setAccountContacts(contactDtos);
              List<AccountPriceProfile> accountPriceProfiles = accountPriceProfileService
                      .findActiveProfilesByAccountId(account.getAccountId());
              if (accountPriceProfiles != null) {
                  accountEntity.setPriceProfiles(new ArrayList<>());
                  accountPriceProfiles.forEach(accountPriceProfile -> {
                      AccountPriceProfileDto profileDto = AccountPriceProfileMapper.INSTANCE.toDto(accountPriceProfile);
                      accountEntity.getPriceProfiles().add(profileDto);
                  });
              }
              accountEntities.add(accountEntity);
          }
          return accountEntities;
      }


      



}
