package com.syncstate.apps.SyncTracker.models.requests;

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
public class CreateScheduledWorkShiftRequest {

    private BigInteger employeeId;

    private LocalDateTime expectedStartTime;

    private LocalDateTime expectedEndTime;

    private Integer expectedBreakPeriodInMins;

    private BigInteger createdByEmployeeId;

}
