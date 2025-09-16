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

    public AccountEntity saveAccountEntity(com.finixone.party.dto.AccountEntity accountEntity) {

        AccountDto accountDto = accountEntity.getAccount();
        if (GenericUtils.isCreateOperation(accountDto.getOperation())) {
            Account account = AccountMapper.INSTANCE.toEntity(accountDto);
            account = accountService.createAccount(account);
            accountDto = AccountMapper.INSTANCE.toDto(account);
            accountEntity.setAccount(accountDto);

            List<AccountPriceProfileDto> profileDtos = accountEntity.getPriceProfiles();
            for (AccountPriceProfileDto profileDto : profileDtos) {
                AccountPriceProfile accountPriceProfile = AccountPriceProfileMapper.INSTANCE.toEntity(profileDto);
                accountPriceProfile.setAccountId(account.getAccountId());
                accountPriceProfileService.create(accountPriceProfile);
                profileDto = AccountPriceProfileMapper.INSTANCE.toDto(accountPriceProfile);
                accountEntity.getPriceProfiles().add(profileDto);
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
