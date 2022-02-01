package ru.timetable.dao.impl;
/*
 * Date: 31.01.2022
 * Time: 6:39 AM
 * */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.PeriodDao;
import ru.timetable.dao.util.PeriodRowMapper;
import ru.timetable.domain.Period;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PeriodDaoImpl implements PeriodDao {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<Period> findById(Integer id) {
        log.debug("findById: searches for a Period with id={}", id);

        String sql = """
                SELECT * FROM period
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new PeriodRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Period period) {
        log.debug("insert: saves the Period to the DB");

        String sql = """
                INSERT INTO period(period_num, first_half, second_half) 
                VALUES (?, ?, ?);
                """;

        try {
            int periodNum = period.getPeriodNum().getAsInt();
            String firstHalf = objectMapper.writeValueAsString(period.getFirstHalf());
            String secondHalf = objectMapper.writeValueAsString(period.getSecondHalf());

            return jdbcTemplate.update(sql, periodNum, firstHalf, secondHalf);
        } catch (DuplicateKeyException | JsonProcessingException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Period with id={}", id);
        jdbcTemplate.update("DELETE FROM period WHERE id = ?;", id);
    }

    @Override
    public void delete(Period period) {
        if (period != null) {
            deleteById(period.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Periods in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM period;");
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Periods");

        String sql = """
                SELECT COUNT(*) AS total
                FROM period;
                """;
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

}
