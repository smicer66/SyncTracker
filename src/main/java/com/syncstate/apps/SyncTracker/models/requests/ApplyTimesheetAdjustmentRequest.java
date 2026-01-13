package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ApplyTimesheetAdjustmentRequest {


    private BigInteger clientId;

    private String adjustmentReason;

    private BigInteger timesheetId;

    private Double adjustmentPeriodInMins;

    private Boolean isIncrease;
}
