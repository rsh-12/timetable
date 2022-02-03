package ru.timetable.dao.util;
/*
 * Date: 03.02.2022
 * Time: 9:43 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;

public class SubjectRowMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        String type = rs.getString("type").toUpperCase();
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

        return Subject.builder()
                .id(rs.getInt("id"))
                .type(SubjectType.valueOf(type))
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
