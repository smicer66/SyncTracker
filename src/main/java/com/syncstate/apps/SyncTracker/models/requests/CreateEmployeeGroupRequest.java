package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class CreateEmployeeGroupRequest {
    private String groupName;
    private BigInteger clientId;
    private BigInteger createdByEmployerId;
}
