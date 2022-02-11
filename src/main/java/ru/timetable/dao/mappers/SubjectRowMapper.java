package ru.timetable.dao.mappers;
/*
 * Date: 03.02.2022
 * Time: 9:43 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;

public class SubjectRowMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        String type = rs.getString("type").toUpperCase();

        return Subject.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .type(SubjectType.valueOf(type))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
    }

}
