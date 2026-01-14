package com.syncstate.apps.SyncTracker.service;

import com.syncstate.apps.SyncTracker.models.SyncTrackerEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SyncTrackerEmailKafkaService {

    @Value("${kafka.email.create.topic}")
    private String createKafkaEmailTopic;

    @Autowired
    private KafkaTemplate<Long, SyncTrackerEmail> kafkaTemplate;

    public void saveSMSToKafkaQueue(SyncTrackerEmail ste)
    {
        this.kafkaTemplate.send(createKafkaEmailTopic, ste);
    }
}
