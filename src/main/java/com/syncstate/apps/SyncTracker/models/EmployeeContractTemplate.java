package com.syncstate.apps.SyncTracker.models;


import com.syncstate.apps.SyncTracker.models.enums.ContractType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee_contract_templates")
public class EmployeeContractTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger employeeContractTemplateId;

    @Column(name = "contractType", nullable = false)
    private ContractType contractType;     // FULL_TIME, PART_TIME, CASUAL

    @Column(name = "hourlyRate", nullable = false)
    private Double hourlyRate;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = true)
    private LocalDate endDate;              // nullable for ongoing contracts

    @Column(name = "maxMinsPerWeek", nullable = false)
    private Double maxMinsPerWeek;

    @Column(name = "overtimeAllowed", nullable = false)
    private Boolean overtimeAllowed;

    @Column(name = "overtimeMultiplier", nullable = true)
    private Double overtimeMultiplier; // e.g. 1.5

    @Column(name = "governingLaw", nullable = true)
    private String governingLaw;

    @Column(name = "contractCode", nullable = true)
    private String contractCode;

    public boolean isActive(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (endDate == null || date.isBefore(endDate));
    }

    public Double calculatePay(Double workedMins) {
        if (!overtimeAllowed || workedMins<=maxMinsPerWeek) {
            return workedMins * (hourlyRate/60);
        }

        Double regularMins = maxMinsPerWeek;
        Double overtimeMins = workedMins - (regularMins);

        return (regularMins * (hourlyRate/60))
                + (overtimeMins * (hourlyRate/60) * (overtimeMultiplier));
    }
}
