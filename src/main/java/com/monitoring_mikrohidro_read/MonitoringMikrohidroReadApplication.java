package com.monitoring_mikrohidro_read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringMikrohidroReadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringMikrohidroReadApplication.class, args);
	}

}
