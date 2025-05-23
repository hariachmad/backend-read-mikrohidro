package com.monitoring_mikrohidro_read.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
import com.monitoring_mikrohidro_read.enitities.Battery;
import com.monitoring_mikrohidro_read.enitities.HumiditySensor;
import com.monitoring_mikrohidro_read.enitities.IntensitySensor;
import com.monitoring_mikrohidro_read.enitities.SolarPanel;
import com.monitoring_mikrohidro_read.enitities.TempSensor;
import com.monitoring_mikrohidro_read.enitities.TurbineCitizens;
import com.monitoring_mikrohidro_read.enitities.TurbineOutput;
import com.monitoring_mikrohidro_read.enitities.events.ElectricityEventV1;
import com.monitoring_mikrohidro_read.exceptions.DatabaseUpdateException;
import com.monitoring_mikrohidro_read.repositories.ElectricityRepository;
import com.monitoring_mikrohidro_read.repositories.LastIdRepository;

@Service
public class ElectricityService {
    @Autowired
    LastIdRepository lastIdRepository;

    @Autowired
    ElectricityRepository electricityRepository;

    @Autowired
    private KafkaTemplate<String, ElectricityEventV1> kafkaTemplate;

    @Value("${electricity.event.electricityTopic}")
    private String topicName;

    public void updateLastId(long lastId, long newLastId) {
        try {
            lastIdRepository.updateLastId(lastId, newLastId);
        } catch (Exception e) {
            throw new DatabaseUpdateException("Failed to update database with new lastId: " + newLastId, e);
        }
    }

    public ElectricityEventV1 publishEvent(ElectricityReadDto event) {

        ElectricityEventV1 electricityEvent = new ElectricityEventV1();
        electricityEvent.setMachineId(1);
        electricityEvent.setEventId(event.getId());
        electricityEvent.setEventVersion("1");
        electricityEvent.setEventTimestamp(event.getTimestamp());
        electricityEvent.setBattery(new Battery(event.getMachineId(),event.getId(),event.getBatteryVoltage(), event.getBatteryCurrentOne(), event.getBatteryCurrentTwo(), event.getTimestamp()));
        electricityEvent.setHumiditySensor(new HumiditySensor(event.getMachineId(),event.getId(), event.getHumidity(), event.getTimestamp()));
        electricityEvent.setIntensitySensor(new IntensitySensor(event.getMachineId(),event.getId(), event.getIntensity(), event.getTimestamp()));
        electricityEvent.setSolarPanel(new SolarPanel(event.getMachineId(),event.getId(), event.getSolarPanelVoltage(), event.getSolarPanelCurrent(), event.getTimestamp()));
        electricityEvent.setTempSensor(new TempSensor(event.getMachineId(),event.getId(), event.getTemperature(), event.getTimestamp()));
        electricityEvent.setTurbineCitizens(new TurbineCitizens(event.getMachineId(),event.getId(), event.getTurbineCitizensVolt(), event.getTurbineCitizensCurrent(), event.getTimestamp()));
        electricityEvent.setTurbineOutput(new TurbineOutput(event.getMachineId(),event.getId(), event.getTurbineOutputVolt(), event.getTurbineOutputCurrent(), event.getTimestamp()));
        System.out.println("Publishing event: " + electricityEvent.toString());

        Message<ElectricityEventV1> message = MessageBuilder
                .withPayload(electricityEvent)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);

        return electricityEvent;
    };

    public boolean publishEvents(List<ElectricityReadDto> events) {
        for (ElectricityReadDto event : events) {
            publishEvent(event);
        }
        return true;
    }

    @Scheduled(fixedRate = 5000)
    public Long checkNewRows() {
        System.out.println("Checking for new rows...");

        long lastId = lastIdRepository.findLastId().getLastId();

        List<ElectricityReadDto> newRows = electricityRepository.findByLastId(lastId);

        if (!newRows.isEmpty()) {
            long newLastId = newRows.get(newRows.size() - 1).getId();
            this.publishEvents(newRows);
            this.updateLastId(lastId, newLastId);
            return newLastId;
        }
        return lastId;
    }
}
