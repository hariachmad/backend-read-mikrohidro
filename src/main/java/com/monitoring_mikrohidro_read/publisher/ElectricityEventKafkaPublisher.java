package com.monitoring_mikrohidro_read.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.monitoring_mikrohidro_read.enitities.events.ElectricityEventV1;

import org.springframework.kafka.core.KafkaTemplate;

@Component
public class ElectricityEventKafkaPublisher {

    @Autowired
    private KafkaTemplate<String, ElectricityEventV1> kafkaTemplate;

    @Value("${electricity.event.topicName}")
    private String topicName;

    public void sendEvent(ElectricityEventV1 electricityEventV1) {
        kafkaTemplate.send(topicName, electricityEventV1);
    }
}