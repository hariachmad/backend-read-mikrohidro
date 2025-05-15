package com.monitoring_mikrohidro_read.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
import com.monitoring_mikrohidro_read.repositories.ElectricityRepository;
import com.monitoring_mikrohidro_read.repositories.LastIdRepository;


@Service
public class ElectricityService {
    @Autowired
    LastIdRepository lastIdRepository;

    @Autowired
    ElectricityRepository electricityRepository;

    @Scheduled(fixedRate = 5000)
    public void checkNewRows(){
        System.out.println("Checking for new rows...");

        long lastId =lastIdRepository.findLastId().getLastId();

        List<ElectricityReadDto> newRows = electricityRepository.findByLastId(lastId);

        if (!newRows.isEmpty()) {
            long newLastId = newRows.get(newRows.size() - 1).getId();
            newRows.forEach(row -> System.out.println("New row: " + row));
            lastIdRepository.updateLastId(lastId,newLastId);
        }
    }
}
