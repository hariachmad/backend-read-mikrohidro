package com.monitoring_mikrohidro_read.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoring_mikrohidro_read.dto.ImageReadDto;
import com.monitoring_mikrohidro_read.services.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/{startDate}/{endDate}")
    public List<ImageReadDto> getImageByDateRange(@PathVariable String startDate, @PathVariable String endDate) {
        return imageService.findByDateRange(startDate, endDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        byte[] image = imageService.findImageById(id);
        return ResponseEntity.ok().body(image);
    }
}
