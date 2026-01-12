package com.syncstate.apps.SyncTracker.models.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String username;
    private String token;
    private String message;
}
