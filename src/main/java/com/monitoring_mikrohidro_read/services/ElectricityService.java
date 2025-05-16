package com.monitoring_mikrohidro_read.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
import com.monitoring_mikrohidro_read.enitities.events.ElectricityEventV1;
import com.monitoring_mikrohidro_read.exceptions.DatabaseUpdateException;
import com.monitoring_mikrohidro_read.publisher.ElectricityEventKafkaPublisher;
import com.monitoring_mikrohidro_read.repositories.ElectricityRepository;
import com.monitoring_mikrohidro_read.repositories.LastIdRepository;


@Service
public class ElectricityService {
    @Autowired
    LastIdRepository lastIdRepository;

    @Autowired
    ElectricityRepository electricityRepository;

    @Autowired
    ElectricityEventKafkaPublisher electricityEventKafkaPublisher;

    @Value("${event.version}")
    String eventVersion;


    public void updateLastId(long lastId, long newLastId) {
        try {
            lastIdRepository.updateLastId(lastId, newLastId);
        } catch (Exception e) {
            throw new DatabaseUpdateException("Failed to update database with new lastId: " + newLastId, e);
        }
    }

    public ElectricityEventV1 publishEvent(ElectricityReadDto event) {

       ElectricityEventV1 electricityEvent = new ElectricityEventV1();
        electricityEvent.setMachineId(event.getMachineId());
        electricityEvent.setEventId(event.getId());
        electricityEvent.setEventVersion(eventVersion);
        electricityEvent.setEventTimestamp(event.getTimestamp());

        electricityEventKafkaPublisher.sendEvent(electricityEvent);

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
