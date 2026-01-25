package com.syncstate.apps.SyncTracker.models.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse extends SmartBankingResponse {
    private String username;
    private String mobileNumber;
}
