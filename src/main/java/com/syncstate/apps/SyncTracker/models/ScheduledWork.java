package com.syncstate.apps.SyncTracker.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.apps.SyncTracker.deserializers.TimestampDeserializer;
import com.syncstate.apps.SyncTracker.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "scheduled_work_schedules")
public class ScheduledWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger scheduledWorkId;

    @Column(name = "employeeId", nullable = false)
    private BigInteger employeeId;

    @Column(name = "clockInTime", nullable = false)
    private LocalDateTime expectedStartTime;

    @Column(name = "clockOutTime", nullable = false)
    private LocalDateTime expectedEndTime;

    @Column(name = "expectedBreakPeriodInMins", nullable = false)
    private Integer expectedBreakPeriodInMins;

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
