package com.syncstate.apps.SyncTracker.models.requests;


import com.syncstate.apps.SyncTracker.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientRequest {
    private String clientName;
    private Address clientAddress;
    private CreateUserRequest createUserRequest;

}
