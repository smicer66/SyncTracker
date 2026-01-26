package com.syncstate.apps.SyncTracker.models.requests;


import com.syncstate.apps.SyncTracker.models.enums.Gender;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
public class CreateNewEmployeeRequest {

    private BigInteger clientId;

    private String employeeNumber;

    private String employeeSocialSecurityNo;

    private LocalDate dateOfBirth;

    private String countryOfOrigin;

    private String mobileNumber;

    private String emailAddress;

    private String firstName;

    private String lastName;

    private String middleName;

    private Gender gender;

    private com.syncstate.apps.SyncTracker.models.enums.MaritalStatus maritalStatus;

    private com.syncstate.apps.SyncTracker.models.enums.CustomerTitle title;
}
