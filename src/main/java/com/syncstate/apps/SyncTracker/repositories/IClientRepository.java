package com.syncstate.apps.SyncTracker.repositories;

import com.syncstate.apps.SyncTracker.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IClientRepository extends JpaRepository<Client, BigInteger> {

    @Query("Select u FROM Client u WHERE u.clientCode = :clientCode")
    Client getClientByClientCode(String clientCode);


    @Query("Select u from Client u WHERE u.clientName = :clientName")
    Client getClientByClientName(String clientName);
}
