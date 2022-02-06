package ru.timetable.dao.mappers;
/*
 * Date: 31.01.2022
 * Time: 6:42 AM
 * */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Period;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

public class PeriodRowMapper implements RowMapper<Period> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Period mapRow(ResultSet rs, int rowNum) throws SQLException {
        int periodNum = rs.getInt("period_num");
        PGobject firstHalfJson = (PGobject) rs.getObject("first_half");
        PGobject secondHalfJson = (PGobject) rs.getObject("second_half");

        try {
            Pair firstHalf = objectMapper.readValue(firstHalfJson.getValue(), Pair.class);
            Pair secondHalf = objectMapper.readValue(secondHalfJson.getValue(), Pair.class);

            return Period.builder()
                    .id(rs.getInt("id"))
                    .periodNum(PeriodNum.valueOf(periodNum))
                    .firstHalf(firstHalf)
                    .secondHalf(secondHalf)
                    .build();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }

}
