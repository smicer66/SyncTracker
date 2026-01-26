package com.syncstate.apps.SyncTracker.models.requests;


import com.syncstate.apps.SyncTracker.models.enums.TokenType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {
    private String token;
    private String data;
    private String clientCode;
    private TokenType tokenType;

}
