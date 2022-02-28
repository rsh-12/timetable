package ru.timetable.dao.impl;
/*
 * Date: 31.01.2022
 * Time: 6:39 AM
 * */

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.dao.PeriodDao;
import ru.timetable.dao.mappers.PeriodRowMapper;
import ru.timetable.domain.Period;
import ru.timetable.domain.util.PeriodNum;
import ru.timetable.util.ServiceUtil;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PeriodDaoImpl implements PeriodDao {

    private final ServiceUtil util;
    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "period";

    @Override
    public Optional<Period> findById(Integer id) {
        log.debug("findById: searches for a Period with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new PeriodRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Period entity) {
        log.debug("insert: saves the Period to the DB");

        String sql = """
                INSERT INTO period(period_num, first_half, second_half) 
                VALUES (?, ? :: jsonb, ? :: jsonb);
                """;

        int periodNum = entity.getPeriodNum().getAsInt();
        String firstHalf = util.toJsonString(entity.getFirstHalf());
        String secondHalf = util.toJsonString(entity.getSecondHalf());

        Integer result = util.handleDuplicateKeyException(() ->
                jdbcTemplate.update(sql, periodNum, firstHalf, secondHalf));

        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Period with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Period entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Periods in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Periods");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Period> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Period> periods = jdbcTemplate.query(sql, new PeriodRowMapper());

        return new PageImpl<>(periods, pageable, count());
    }

    @Override
    @Transactional
    public void insertAll(List<Period> entities) {
        log.debug("insertAll: inserts multiple entities");

        String sql = """
                INSERT INTO period(period_num, first_half, second_half)
                VALUES (?, ? :: jsonb, ? :: jsonb);
                """;

        util.handleDuplicateKeyException(
                () -> jdbcTemplate.batchUpdate(sql, entities, entities.size(), (ps, argument) -> {
                    ps.setInt(1, argument.getPeriodNum().getAsInt());
                    ps.setObject(2, util.toJsonString(argument.getFirstHalf()));
                    ps.setObject(3, util.toJsonString(argument.getSecondHalf()));
                }));
    }

    @Override
    public Optional<Period> findByPeriodNum(PeriodNum periodNum) {
        log.debug("findByPeriodNum: searches for a Period={}", periodNum.getAsInt());

        String sql = """
                SELECT * FROM period
                WHERE period_num = ?;
                """;

        return jdbcTemplate.query(sql, new PeriodRowMapper(), periodNum.getAsInt())
                .stream().findFirst();
    }

}
