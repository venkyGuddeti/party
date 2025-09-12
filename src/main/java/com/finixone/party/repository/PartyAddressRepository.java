package com.finixone.party.repository;

public interface PartyAddressRepository extends org.springframework.data.jpa.repository.JpaRepository<com.finixone.party.model.PartyAddress, java.util.UUID> {
    java.util.List<com.finixone.party.model.PartyAddress> findByPartyId(java.util.UUID partyId);
    java.util.List<com.finixone.party.model.PartyAddress> findByAddressType(String addressType);
    

}
