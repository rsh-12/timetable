package ru.timetable.dao.mappers;
/*
 * Date: 03.02.2022
 * Time: 9:43 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;

public class SubjectRowMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubjectType type = Optional.ofNullable(rs.getString("type"))
                .map(String::toUpperCase)
                .map(SubjectType::valueOf)
                .orElse(SubjectType.LECTURE);

        return Subject.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .type(type)
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
    }

}
