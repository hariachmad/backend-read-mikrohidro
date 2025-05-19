package com.monitoring_mikrohidro_read.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
import com.monitoring_mikrohidro_read.dto.LastIdDto;
import com.monitoring_mikrohidro_read.repositories.ElectricityRepository;
import com.monitoring_mikrohidro_read.repositories.LastIdRepository;

@ExtendWith(MockitoExtension.class)
public class ElectricityServiceTest {
    @Mock
    LastIdRepository lastIdRepository;

    @Mock
    ElectricityRepository electricityRepository;

    @InjectMocks
    ElectricityService electricityService;

    // @Test
    public void testCheckNewRows() {
        LastIdDto mockLastIdDto = new LastIdDto(1L); // Mock lastIdDto with a specific lastId
        List<ElectricityReadDto> mockElectricityReadDto = new ArrayList<>();
        mockElectricityReadDto.add(new ElectricityReadDto(
                2L, // id,
                1,
                25.5f, // temperature
                60.2f, // humidity
                1000.0f, // intensity
                12.3f, // solarPanelVoltage
                5.2f, // solarPanelCurrent
                11.8f, // batteryVoltage
                3.1f, // batteryCurrentOne
                3.2f, // batteryCurrentTwo
                220.0f, // turbineOutputVolt
                15.0f, // turbineOutputCurrent
                220.0f, // turbineCitizensVolt
                12.5f, // turbineCitizensCurrent
                LocalDateTime.now() // timestamp
        ));

        mockElectricityReadDto.add(new ElectricityReadDto(
                3L, // id
                1,
                25.5f, // temperature
                60.2f, // humidity
                1000.0f, // intensity
                12.3f, // solarPanelVoltage
                5.2f, // solarPanelCurrent
                11.8f, // batteryVoltage
                3.1f, // batteryCurrentOne
                3.2f, // batteryCurrentTwo
                220.0f, // turbineOutputVolt
                15.0f, // turbineOutputCurrent
                220.0f, // turbineCitizensVolt
                12.5f, // turbineCitizensCurrent
                LocalDateTime.now() // timestamp
        ));

        when(lastIdRepository.findLastId()).thenReturn(mockLastIdDto);
        when(electricityRepository.findByLastId(mockLastIdDto.getLastId())).thenReturn(mockElectricityReadDto);
        // Mock the behavior of lastIdRepository and electricityRepository
        // to return specific values for testing
        // Call the method to be tested
        long result = electricityService.checkNewRows();
        verify(lastIdRepository).findLastId();
        verify(electricityRepository).findByLastId(mockLastIdDto.getLastId());
        assertEquals(3L, result);

        // Verify the interactions and assert the expected results
        // You can use Mockito's verify() and assertEquals() methods here
    }
}
