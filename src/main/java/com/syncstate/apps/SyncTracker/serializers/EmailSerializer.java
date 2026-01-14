package com.syncstate.apps.SyncTracker.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import com.syncstate.apps.SyncTracker.models.SyncTrackerEmail;

import java.util.Map;


public class EmailSerializer implements Serializer<SyncTrackerEmail> {


    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, SyncTrackerEmail ste) {
        try {
            return this.objectMapper.writeValueAsBytes(ste);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, SyncTrackerEmail data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
