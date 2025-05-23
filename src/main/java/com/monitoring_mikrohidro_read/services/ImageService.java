package com.monitoring_mikrohidro_read.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.monitoring_mikrohidro_read.dto.ImageReadDto;
import com.monitoring_mikrohidro_read.enitities.Image;
import com.monitoring_mikrohidro_read.enitities.events.ElectricityEventV1;
import com.monitoring_mikrohidro_read.enitities.events.ImageEventV1;
import com.monitoring_mikrohidro_read.exceptions.DatabaseUpdateException;
import com.monitoring_mikrohidro_read.repositories.ImageRepository;
import com.monitoring_mikrohidro_read.repositories.LastIdImagesRepository;

@Service
public class ImageService {
    @Autowired
    LastIdImagesRepository lastIdImagesRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private KafkaTemplate<String, ElectricityEventV1> kafkaTemplate;

    @Value("${electricity.event.imageTopic}")
    private String topicName;

    public void updateLastId(long lastId, long newLastId) {
        try {
            lastIdImagesRepository.updateLastId(lastId, newLastId);
        } catch (Exception e) {
            throw new DatabaseUpdateException("Failed to update database with new lastIdImages: " + newLastId, e);
        }
    }

    public ImageEventV1 publishEvent(ImageReadDto event) {
        ImageEventV1 imageEvent = new ImageEventV1();
        imageEvent.setMachineId(1);
        imageEvent.setEventId(event.getId());
        imageEvent.setEventVersion("1");
        imageEvent.setEventTimestamp(event.getTimestamp());
        imageEvent.setImage(new Image(event.getMachineId(),event.getId(), event.getCameraId(), event.getData(), event.getTimestamp()));
        System.out.println("Publishing event image: " + imageEvent.toString());

        Message<ImageEventV1> message = MessageBuilder
                .withPayload(imageEvent)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);

        return imageEvent;
    };

    public boolean publishEvents(List<ImageReadDto> events) {
        for (ImageReadDto event : events) {
            publishEvent(event);
        }
        return true;
    }

    @Scheduled(fixedRate = 50000)
    public Long checkNewRows() {
        System.out.println("Checking for new image...");

        long lastId = lastIdImagesRepository.findLastId().getLastId();

        List<ImageReadDto> newRows = imageRepository.findByLastId(lastId);

        if (!newRows.isEmpty()) {
            long newLastId = newRows.get(newRows.size() - 1).getId();
            this.publishEvents(newRows);
            this.updateLastId(lastId, newLastId);
            return newLastId;
        }

        return lastId;
    }
}
