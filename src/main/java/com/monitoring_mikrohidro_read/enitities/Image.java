package com.monitoring_mikrohidro_read.enitities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private long id;
    private long cameraId;
    private long machineId;
    private byte[] data;
    LocalDateTime timestamp;
}