package com.syncstate.apps.SyncTracker.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.apps.SyncTracker.deserializers.TimestampDeserializer;
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
@Table(name = "timesheets")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger timesheetId;

    @Column(name = "scheduledWorkId", nullable = true)
    private BigInteger scheduledWorkId;

    @Column(name = "employeeId", nullable = false)
    private BigInteger employeeId;

    @Column(name = "clientId", nullable = false)
    private BigInteger clientId;

    @Column(name = "clockInTime", nullable = false)
    private LocalDateTime clockInTime;

    @Column(name = "clockOutTime", nullable = true)
    private LocalDateTime clockOutTime;

    @Column(name = "clockOutBySystem", nullable = true)
    private Boolean clockOutBySystem;

    @Column(name = "isApproved", nullable = true)
    private Boolean isApproved;

    @Column(name = "approvedByEmployeeId", nullable = true)
    private BigInteger approvedByEmployeeId;

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
