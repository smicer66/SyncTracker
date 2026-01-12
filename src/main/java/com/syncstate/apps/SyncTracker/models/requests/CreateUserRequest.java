package com.syncstate.apps.SyncTracker.models.requests;


import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String mobileNumber;
    private String emailAddress;
    private String password;


}
