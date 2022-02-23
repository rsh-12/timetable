package ru.timetable.dao.impl;
/*
 * Date: 27.01.2022
 * Time: 6:49 AM
 * */

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.dao.DayDao;
import ru.timetable.dao.mappers.DayRowMapper;
import ru.timetable.domain.Day;
import ru.timetable.domain.util.Weekday;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DayDaoImpl implements DayDao {

    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "day";

    @Override
    public Optional<Day> findById(Integer id) {
        log.debug("findById: searches for a Day with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new DayRowMapper(), id).stream()
                .findFirst();
    }

    @Override
    public int insert(Day entity) {
        log.debug("insert: saves the Day to the DB");

        String sql = """
                INSERT INTO %s(name)
                VALUES (?);
                """.formatted(TABLE);

        try {
            return jdbcTemplate.update(sql, entity.getName().name());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Day with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Day entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Days in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Days");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Day> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Day> days = jdbcTemplate.query(sql, new DayRowMapper());

        return new PageImpl<>(days, pageable, count());
    }

    @Override
    @Transactional
    public void insertAll(List<Day> entities) {
    }

    @Override
    public Optional<Day> findByWeekday(Weekday day) {
        log.debug("findByWeekday: searches for a Day {}", day.name());

        String sql = """
                SELECT * FROM %s
                WHERE name = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new DayRowMapper(), day.name())
                .stream().findFirst();
    }

}
