package ru.timetable.dao.impl;

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
import ru.timetable.dao.AudienceDao;
import ru.timetable.dao.mappers.AudienceRowMapper;
import ru.timetable.domain.Audience;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AudienceDaoImpl implements AudienceDao {

    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "audience";

    @Override
    public Optional<Audience> findById(Integer id) {
        log.debug("findById: searches for an Audience with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new AudienceRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Audience entity) {
        log.debug("insert: saves the Audience to the DB");

        String sql = """
                    INSERT INTO %s(number)
                    VALUES (?);
                """.formatted(TABLE);

        try {
            return jdbcTemplate.update(sql, entity.getNumber());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete an Audience with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Audience entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Audiences in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public Optional<Audience> findByNumber(String number) {
        log.debug("findByNumber: searches for an Audience with number={}", number);

        String sql = """
                SELECT * FROM %s
                WHERE number = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new AudienceRowMapper(), number)
                .stream().findFirst();
    }

    @Override
    @Transactional
    public void insertAll(List<Audience> entities) {
        log.debug("insertAll: inserts multiple entities");

        String sql = """
                INSERT INTO audience(number) VALUES (?)
                """;

        jdbcTemplate.batchUpdate(sql, entities, entities.size(), (ps, argument) ->
                ps.setString(1, argument.getNumber()));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Audiences");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Audience> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Audience> audiences = jdbcTemplate.query(sql, new AudienceRowMapper());

        return new PageImpl<>(audiences, pageable, count());
    }

}
