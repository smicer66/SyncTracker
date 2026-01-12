package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IUserRepository extends JpaRepository<User, BigInteger> {

    @Query("Select tp from User tp WHERE tp.deletedAt IS NULL AND tp.username = :username")
    User getUserByUsername(String username);
}
