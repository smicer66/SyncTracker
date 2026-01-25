package com.syncstate.apps.SyncTracker.repositories;

import com.syncstate.apps.SyncTracker.models.Client;
import com.syncstate.apps.SyncTracker.models.ClientDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IClientDomainRepository extends JpaRepository<ClientDomain, BigInteger> {

    @Query("Select u FROM ClientDomain u WHERE u.domain = :domain")
    ClientDomain getClientDomainByDomain(String domain);

    @Query("Select c FROM ClientDomain u " +
            "INNER JOIN Client c on c.clientId = u.clientId WHERE u.domain = :domain AND u.isCurrent = true")
    Client getClientByDomain(String domain);


    @Query("SELECT tp from ClientDomain tp " +
            "WHERE tp.clientId = :clientId AND tp.isCurrent = true")
    ClientDomain getActiveClientDomainByClientId(BigInteger clientId);
}
