package ru.timetable.dao.impl;
/*
 * Date: 27.01.2022
 * Time: 6:49 AM
 * */

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.DayDao;
import ru.timetable.dao.util.DayRowMapper;
import ru.timetable.domain.Day;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DayDaoImpl implements DayDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Day> findById(Integer id) {
        log.debug("findById: searches for a Day with id={}", id);

        String sql = """
                SELECT * FROM day
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new DayRowMapper(), id).stream()
                .findFirst();
    }

    @Override
    public int insert(Day day) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
