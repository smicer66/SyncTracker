package com.syncstate.apps.SyncTracker.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.apps.SyncTracker.deserializers.TimestampDeserializer;
import com.syncstate.apps.SyncTracker.models.enums.Gender;
import com.syncstate.apps.SyncTracker.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "employee_bio_data")
public class EmployeeBioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger employeeBioDataId;

    @Column(name = "employeeId", nullable = true)
    private BigInteger employeeId;

    @Column(name = "clientId", nullable = true)
    private BigInteger clientId;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "middleName", nullable = true)
    private String middleName;

    @Column(name = "emailAddress", nullable = true)
    private String emailAddress;

    @Column(name = "mobileNumber", nullable = false)
    private String mobileNumber;

    @Column(name = "gender", nullable = true)
    private Gender gender;

    @Column(name = "maritalStatus", nullable = true)
    private com.syncstate.apps.SyncTracker.models.enums.MaritalStatus maritalStatus;

    @Column(name = "title", nullable = true)
    private com.syncstate.apps.SyncTracker.models.enums.CustomerTitle title;

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
