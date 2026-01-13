package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTimesheetClockInRequest {


    private BigInteger clientId;

    private String noteContent;

}
