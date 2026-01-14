package com.syncstate.apps.SyncTracker.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class SendInvitationRequest {
    private BigInteger employeeId;
    private BigInteger clientId;
    private BigInteger emailAddress;
}
