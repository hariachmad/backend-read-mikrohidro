package com.monitoring_mikrohidro_read.enitities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntensitySensor {
    private long id;
    private long machineId;
    private float intensity;
    private LocalDateTime timestamp;
}