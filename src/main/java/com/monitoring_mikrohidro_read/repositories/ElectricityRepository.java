package com.monitoring_mikrohidro_read.repositories;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.monitoring_mikrohidro_read.dto.ElectricityReadDto;
import com.monitoring_mikrohidro_read.dto.ImageReadDto;

@Repository
public class ElectricityRepository {
    private final JdbcTemplate jdbcTemplate;

    public ElectricityRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<ElectricityReadDto> electricityRowMapper = (rs, rowNum) -> {
        ElectricityReadDto electricityReadDto = new ElectricityReadDto();
        electricityReadDto.setBatteryCurrentOne(rs.getFloat("battery_current_one"));
        electricityReadDto.setBatteryCurrentTwo(rs.getFloat("battery_current_two"));
        electricityReadDto.setBatteryVoltage(rs.getFloat("battery_voltage"));
        electricityReadDto.setHumidity(rs.getFloat("humidity"));
        electricityReadDto.setId(rs.getLong("id"));
        electricityReadDto.setIntensity(rs.getFloat("intensity"));
        electricityReadDto.setSolarPanelCurrent(rs.getFloat("solar_panel_current"));
        electricityReadDto.setSolarPanelVoltage(rs.getFloat("solar_panel_voltage"));
        electricityReadDto.setTemperature(rs.getFloat("temperature"));
        electricityReadDto.setTurbineCitizensCurrent(rs.getFloat("turbine_citizens_current"));
        electricityReadDto.setTurbineCitizensVolt(rs.getFloat("turbine_citizens_volt"));
        electricityReadDto.setTurbineOutputCurrent(rs.getFloat("turbine_output_current"));
        electricityReadDto.setTurbineOutputVolt(rs.getFloat("turbine_output_volt"));
        electricityReadDto.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return electricityReadDto;
    };

    public List<ElectricityReadDto> findAll() {
        String sql = "SELECT * FROM electricity";
        return jdbcTemplate.query(sql, electricityRowMapper);
    }

    public List<ElectricityReadDto> findByLastId(long id) {
        String sql = "SELECT * FROM electricity WHERE id > ?";
        return jdbcTemplate.query(sql, new Object[]{id}, electricityRowMapper);
    }

    public List<ElectricityReadDto> findByDateRange(String startDate, String endDate) {
        String sql = "SELECT * FROM electricity WHERE timestamp between ? and ? limit 10";
        return jdbcTemplate.query(sql, new Object[]{startDate,endDate}, electricityRowMapper);
    }

    public ElectricityReadDto findById(Long id){
        String sql = "SELECT * FROM electricity WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, electricityRowMapper).get(0);
    }
}
