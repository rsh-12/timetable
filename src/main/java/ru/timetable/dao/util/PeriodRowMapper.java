package ru.timetable.dao.util;
/*
 * Date: 31.01.2022
 * Time: 6:42 AM
 * */

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.timetable.domain.Period;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

public class PeriodRowMapper implements RowMapper<Period> {

    @Override
    public Period mapRow(ResultSet rs, int rowNum) throws SQLException {
        int periodNum = rs.getInt("period_num");
        Pair<String, String> firstHalf = rs.getObject("first_half", Pair.class);
        Pair<String, String> secondHalf = rs.getObject("second_half", Pair.class);

        return Period.builder()
                .id(rs.getInt("id"))
                .periodNum(PeriodNum.valueOf(periodNum))
                .firstHalf(firstHalf)
                .secondHalf(secondHalf)
                .build();
    }

}
