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
@Table(name = "addresses")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger addressId;

    @Column(name = "addressLine1", nullable = false)
    private String addressLine1;

    @Column(name = "addressLine2", nullable = true)
    private String addressLine2;

    @Column(name = "addressLine3", nullable = true)
    private String addressLine3;

    @Column(name = "addressLine4", nullable = true)
    private String addressLine4;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "countryId", nullable = false)
    private BigInteger countryId;


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
