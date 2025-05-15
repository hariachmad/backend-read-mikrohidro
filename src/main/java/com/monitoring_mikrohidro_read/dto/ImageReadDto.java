package com.monitoring_mikrohidro_read.dto;

import java.time.LocalDateTime;

public record ImageReadDto(
    float id,
    byte[] data,
    LocalDateTime timestamp){}