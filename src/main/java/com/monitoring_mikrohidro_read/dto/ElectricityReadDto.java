package com.monitoring_mikrohidro_read.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricityReadDto {
        private long id;
        private long machineId;
        private float temperature;
        private float humidity;
        private float intensity;
        private float solarPanelVoltage;
        private float solarPanelCurrent;
        private float batteryVoltage;
        private float batteryCurrentOne;
        private float batteryCurrentTwo;
        private float turbineOutputVolt;
        private float turbineOutputCurrent;
        private float turbineCitizensVolt;
        private float turbineCitizensCurrent;
        private LocalDateTime timestamp;
}
