package com.monitoring_mikrohidro_read.repositories;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.monitoring_mikrohidro_read.dto.LastIdDto;

@Repository
public class LastIdRepository {
    private final JdbcTemplate jdbcTemplate;
    public LastIdRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LastIdDto> lastIdRowMapper = (rs, rowNum) -> {
        LastIdDto lastIdDto = new LastIdDto();
        lastIdDto.setLastId(rs.getLong("last_id"));
        return lastIdDto;
    };
    
    public LastIdDto findLastId() {
        String sql = "SELECT last_id FROM last_id";
        return jdbcTemplate.queryForObject(sql, lastIdRowMapper);
    }

    public long updateLastId(long lastId,long newLastId) {
        String sql = "UPDATE last_id SET last_id = ? where last_id = ?";
        jdbcTemplate.update(sql, newLastId,lastId);
        return newLastId;
    }
}
