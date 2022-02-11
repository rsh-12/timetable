package ru.timetable.dao.mappers;
/*
 * Date: 11.02.2022
 * Time: 8:37 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Teacher;
import ru.timetable.domain.util.Gender;

public class TeacherRowMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Teacher teacher = Teacher.builder()
                .id(rs.getInt("id"))
                .lastName(rs.getString("last_name"))
                .firstName(rs.getString("first_name"))
                .middleName(rs.getString("middle_name"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .build();

        Optional.ofNullable(rs.getString("gender"))
                .map(String::toUpperCase)
                .map(Gender::valueOf)
                .ifPresent(teacher::setGender);

        return teacher;
    }

}
