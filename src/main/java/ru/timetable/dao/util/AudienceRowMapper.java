package ru.timetable.dao.util;
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
        Audience audience = new Audience();

        audience.setId(rs.getInt("id"));
        audience.setNumber(rs.getString("number"));
        audience.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        audience.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());

        return audience;
    }
}
