package com.monitoring_mikrohidro_read.repositories;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.monitoring_mikrohidro_read.dto.ImageReadDto;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    public ImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ImageReadDto> imageRowMapper = (rs, rowNum) -> {
        ImageReadDto imageReadDto = new ImageReadDto();
        // electricityReadDto.setBatteryCurrentOne(rs.getFloat("battery_current_one"));
        imageReadDto.setCameraId(rs.getLong("camera_id"));
        imageReadDto.setId(rs.getLong("id"));
        imageReadDto.setData(rs.getBytes("data"));
        imageReadDto.setMachineId(rs.getLong("machine_id"));
        imageReadDto.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return imageReadDto;
    };

    public List<ImageReadDto> findAll() {
        String sql = "SELECT * FROM image";
        return jdbcTemplate.query(sql, imageRowMapper);
    }

    public List<ImageReadDto> findByLastId(long id) {
        String sql = "SELECT * FROM image WHERE id > ?";
        return jdbcTemplate.query(sql, new Object[]{id}, imageRowMapper);
    }

    public List<ImageReadDto> findByDateRange(String startDate, String endDate) {
        String sql = "SELECT * FROM image WHERE timestamp between ? and ? order by id desc limit 10";
        return jdbcTemplate.query(sql, new Object[]{startDate,endDate}, imageRowMapper);
    }

    public ImageReadDto findImageById(long id) {
        String sql = "SELECT * FROM image WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, imageRowMapper).get(0);
    }
}