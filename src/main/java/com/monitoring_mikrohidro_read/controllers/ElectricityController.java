package com.monitoring_mikrohidro_read.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
// import com.monitoring_mikrohidro_read.dto.ImageReadDto;
import com.monitoring_mikrohidro_read.services.ElectricityService;

@RestController
@RequestMapping("/electricity")
public class ElectricityController {
    @Autowired
    private ElectricityService electricityService;

    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<List<ElectricityReadDto>> getElectricityByDateRange(@PathVariable String startDate, @PathVariable String endDate) {
        return ResponseEntity.ok().body(electricityService.findByDateRange(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricityReadDto> getImageById(@PathVariable Long id) {
        ElectricityReadDto electricity = electricityService.findById(id) ;
        return ResponseEntity.ok().body(electricity);
    }
}
