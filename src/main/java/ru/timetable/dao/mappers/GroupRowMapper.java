package ru.timetable.dao.mappers;
/*
 * Date: 06.02.2022
 * Time: 1:25 PM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Group;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Group.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .department(rs.getString("department"))
                .createdAt(rs.getTimestamp("created_at").toInstant())
                .updatedAt(rs.getTimestamp("updated_at").toInstant())
                .build();
    }

}
