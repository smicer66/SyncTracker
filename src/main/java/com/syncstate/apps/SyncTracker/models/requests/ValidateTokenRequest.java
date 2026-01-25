package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {
    private String token;
    private String data;
    private com.probase.potzr.SmartBanking.models.enums.TokenType tokenType;

}
