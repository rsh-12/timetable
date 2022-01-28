package ru.timetable.dao.util;
/*
 * Date: 28.01.2022
 * Time: 6:22 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Day;
import ru.timetable.domain.Weekday;

public class DayRowMapper implements RowMapper<Day> {

    @Override
    public Day mapRow(ResultSet rs, int rowNum) throws SQLException {
        Day day = new Day();
        day.setId(rs.getInt("id"));
        day.setName(Weekday.valueOf(rs.getString("name").toUpperCase()));

        return day;
    }
}
