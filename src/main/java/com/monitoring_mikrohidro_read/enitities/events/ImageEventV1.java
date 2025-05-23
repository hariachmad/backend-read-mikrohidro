package com.monitoring_mikrohidro_read.enitities.events;

import java.time.LocalDateTime;

import com.monitoring_mikrohidro_read.enitities.Image;

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
    private Image image;
}