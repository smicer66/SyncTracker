package com.syncstate.apps.SyncTracker.repositories;

import com.syncstate.apps.SyncTracker.models.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IClientUserRepository extends JpaRepository<ClientUser, BigInteger> {

    @Query("Select u FROM ClientUser u WHERE u.clientId = :clientId")
    ClientUser getClientUserByClientCode(BigInteger clientId);


}
