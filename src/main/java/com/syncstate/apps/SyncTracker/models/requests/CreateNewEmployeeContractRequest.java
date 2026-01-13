package com.syncstate.apps.SyncTracker.models.requests;


import com.syncstate.apps.SyncTracker.models.enums.ContractType;
import com.syncstate.apps.SyncTracker.models.enums.Gender;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
public class CreateNewEmployeeContractRequest {

    private BigInteger clientId;

    private BigInteger employeeId;

    private ContractType contractType;     // FULL_TIME, PART_TIME, CASUAL

    private Double hourlyRate;

    private LocalDate startDate;

    private LocalDate endDate;              // nullable for ongoing contracts

    private Double maxMinsPerWeek;

    private Boolean overtimeAllowed;

    private Double overtimeMultiplier; // e.g. 1.5

    private String governingLaw;
}
