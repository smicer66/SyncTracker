package com.syncstate.apps.SyncTracker.repositories;


import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.syncstate.apps.SyncTracker.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ITokenRepository extends JpaRepository<Token, BigInteger> {

    @Query("Select tp from Token tp WHERE tp.deletedAt IS NULL AND " +
            "tp.tokenOwnedByEntityId = :tokenOwnedByEntityId AND " +
            "tp.usedAt IS NULL AND tp.token = :token AND " +
            "tp.tokenType = :tokenType AND " +
            "tp.expiredAt > CURRENT_TIMESTAMP")
    public Token getValidToken(BigInteger tokenOwnedByEntityId, String token, TokenType tokenType);
}
