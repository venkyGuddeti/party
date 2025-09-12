package com.finixone.party.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finixone.party.model.Party;

public interface PartyRepository extends JpaRepository<com.finixone.party.model.Party, java.util.UUID> {
    List<com.finixone.party.model.Party> findByPartyName(String partyName);
    List<com.finixone.party.model.Party> findByEmail(String email);
    List<com.finixone.party.model.Party> findByPrimaryIdType(String primaryIdType);

    @Query(
    "SELECT p FROM Party p  " +
    "WHERE " +
    "(:partyName IS NULL OR :partyName='' OR p.partyNameUpper LIKE CONCAT('%', :partyName, '%')) " +
    "AND (:partyType IS NULL OR :partyType='' OR p.partyType = :partyType) " +
    "AND (:partyStatus IS NULL OR :partyStatus='' OR p.partyStatus = :partyStatus) " +
    "AND (:primaryIdType IS NULL OR :primaryIdType='' OR p.primaryIdType = :primaryIdType) " +
    "AND (:primaryIdNumber IS NULL OR :primaryIdNumber='' OR p.primaryIdNumber LIKE CONCAT('%', :primaryIdNumber, '%')) " +
    "AND (:segment IS NULL OR :segment='' OR p.segment = :segment) "
  )
  org.springframework.data.domain.Page<Party> findParties(
    @Param("partyName") String partyName,
    @Param("partyType") String partyType,
    @Param("partyStatus") String partyStatus,
    @Param("primaryIdType") String primaryIdType,
    @Param("primaryIdNumber") String primaryIdNumber,
    @Param("segment") String segment,
    org.springframework.data.domain.Pageable pageable
  );

}
