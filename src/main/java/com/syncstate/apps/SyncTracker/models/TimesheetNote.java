package com.syncstate.apps.SyncTracker.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.apps.SyncTracker.deserializers.TimestampDeserializer;
import com.syncstate.apps.SyncTracker.models.enums.TimesheetActivityType;
import com.syncstate.apps.SyncTracker.serializers.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "timesheet_notes")
public class TimesheetNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger timesheetNoteId;

    @Column(name = "timesheetId", nullable = false)
    private BigInteger timesheetId;

    @Column(name = "note_content", nullable = false)
    private String noteContent;

    @Column(name = "note_content", nullable = true)
    private TimesheetActivityType timesheetActivityType;

    @Column(name = "noteCreatedByEmployeeId", nullable = true)
    private BigInteger noteCreatedByEmployeeId;

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
