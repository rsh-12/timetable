package ru.timetable.dao.mappers;
/*
 * Date: 21.01.2022
 * Time: 8:06 PM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Audience;

public class AudienceRowMapper implements RowMapper<Audience> {

    @Override
    public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Audience.builder()
                .id(rs.getInt("id"))
                .number(rs.getString("number"))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
    }

}
