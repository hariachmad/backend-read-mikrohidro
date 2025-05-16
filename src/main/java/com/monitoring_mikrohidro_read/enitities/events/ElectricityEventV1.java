package com.monitoring_mikrohidro_read.enitities.events;

import java.time.LocalDateTime;

import com.monitoring_mikrohidro_read.enitities.Battery;
import com.monitoring_mikrohidro_read.enitities.HumiditySensor;
import com.monitoring_mikrohidro_read.enitities.IntensitySensor;
import com.monitoring_mikrohidro_read.enitities.SolarPanel;
import com.monitoring_mikrohidro_read.enitities.TempSensor;
import com.monitoring_mikrohidro_read.enitities.TurbineCitizens;
import com.monitoring_mikrohidro_read.enitities.TurbineOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricityEventV1 {
    long machineId;
    long eventId;
    String eventVersion;
    LocalDateTime eventTimestamp;

    Battery battery;
    HumiditySensor humiditySensor;
    IntensitySensor intensitySensor;
    SolarPanel solarPanel;
    TempSensor tempSensor;
    TurbineCitizens turbineCitizens;
    TurbineOutput turbineOutput;
}
