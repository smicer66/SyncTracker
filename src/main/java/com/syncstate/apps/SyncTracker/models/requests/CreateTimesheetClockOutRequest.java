package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class CreateTimesheetClockOutRequest {


    private BigInteger clientId;

    private String noteContent;

    private BigInteger timeSheetId;

}
