package com.monitoring_mikrohidro_read.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.monitoring_mikrohidro_read.dto.LastIdImagesDto;

@Repository
public class LastIdImagesRepository {
    private final JdbcTemplate jdbcTemplate;
    public LastIdImagesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LastIdImagesDto> lastIdRowMapper = (rs, rowNum) -> {
        LastIdImagesDto lastIdImagesDto = new LastIdImagesDto();
        lastIdImagesDto.setLastId(rs.getLong("last_id"));
        return lastIdImagesDto;
    };
    
    public LastIdImagesDto findLastId() {
        String sql = "SELECT last_id FROM last_id_images";
        return jdbcTemplate.queryForObject(sql, lastIdRowMapper);
    }

    public long updateLastId(long lastId,long newLastId) {
        String sql = "UPDATE last_id_images SET last_id = ? where last_id = ?";
        jdbcTemplate.update(sql, newLastId,lastId);
        return newLastId;
    }
}
