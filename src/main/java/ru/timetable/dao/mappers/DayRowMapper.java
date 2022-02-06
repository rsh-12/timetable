package ru.timetable.dao.mappers;
/*
 * Date: 28.01.2022
 * Time: 6:22 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Day;
import ru.timetable.domain.util.Weekday;

public class DayRowMapper implements RowMapper<Day> {

    @Override
    public Day mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Day.builder()
                .id(rs.getInt("id"))
                .name(Weekday.valueOf(rs.getString("name").toUpperCase()))
                .build();
    }

}
