package com.monitoring_mikrohidro_read.enitities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurbineCitizens {
    private long id;
    private float voltage;
    private float current;
    private LocalDateTime timestamp;
}