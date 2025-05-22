package com.monitoring_mikrohidro_read.enitities.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEventV1 {
    long machineId;
    long eventId;
    String eventVersion;
    LocalDateTime eventTimestamp;
    long cameraId;
    byte[] data;

    public ImageEventV1(long machineId, long eventId, String eventVersion, LocalDateTime eventTimestamp, byte[] data,long cameraId) {
        this.machineId = machineId;
        this.eventId = eventId;
        this.eventVersion = eventVersion;
        this.eventTimestamp = eventTimestamp;
        this.data = data;
        this.cameraId = cameraId;
    }
}