package com.monitoring_mikrohidro_read.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageReadDto {
    private long id;
    private byte[] data;
    private LocalDateTime timestamp;
    private long cameraId;
    private long machineId;
}