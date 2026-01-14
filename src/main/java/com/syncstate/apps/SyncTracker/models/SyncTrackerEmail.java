package com.syncstate.apps.SyncTracker.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncTrackerEmail {

    private String emailMessage;
    private String emailSubject;
    private String emailRecipient;
}
