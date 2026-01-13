package com.syncstate.apps.SyncTracker.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.probase.potzr.SmartBanking.models.enums.CustomerTitle;
import com.probase.potzr.SmartBanking.models.enums.MaritalStatus;
import com.syncstate.apps.SyncTracker.deserializers.TimestampDeserializer;
import com.syncstate.apps.SyncTracker.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger employeeId;

    @Column(name = "clientId", nullable = false)
    private BigInteger clientId;

    @Column(name = "userId", nullable = true)
    private BigInteger userId;

    @Column(name = "employeeNumber", nullable = true)
    private String employeeNumber;

    @Column(name = "employeeSocialSecurityNo", nullable = true)
    private String employeeSocialSecurityNo;

    @Column(name = "dateOfBirth", nullable = true)
    private LocalDate dateOfBirth;

    @Column(name = "countryOfOrigin", nullable = true)
    private String countryOfOrigin;

    @Column(name = "currentEmployeeAddressId", nullable = true)
    private BigInteger currentEmployeeAddressId;

    @Column(name = "identificationDocumentId", nullable = true)
    private BigInteger identificationDocumentId;

    @Column(name = "createdAt", nullable = false)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt", nullable = true)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime deletedAt;


    @PrePersist
    public void onCreate()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
