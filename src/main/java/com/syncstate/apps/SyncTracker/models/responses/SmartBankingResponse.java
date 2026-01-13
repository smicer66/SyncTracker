package com.syncstate.apps.SyncTracker.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SmartBankingResponse {
    private int statusCode;
    private String message;
    private Map responseObject;

}
