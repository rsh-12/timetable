package ru.timetable.dao.impl;
/*
 * Date: 27.01.2022
 * Time: 6:49 AM
 * */

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
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
        log.debug("insert: saves the Day to the DB");

        String sql = """
                INSERT INTO day(name)
                VALUES (?);
                """;

        try {
            return jdbcTemplate.update(sql, day.getName().name());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Days in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM day;");
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Days");

        String sql = """
                select count(*) as total
                from day;
                """;
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

}
