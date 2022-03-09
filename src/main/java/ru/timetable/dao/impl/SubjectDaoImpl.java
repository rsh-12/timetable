package ru.timetable.dao.impl;
/*
 * Date: 02.02.2022
 * Time: 6:41 AM
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
import ru.timetable.dao.SubjectDao;
import ru.timetable.dao.mappers.SubjectRowMapper;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;
import ru.timetable.util.ServiceUtil;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SubjectDaoImpl implements SubjectDao {

    private final ServiceUtil util;
    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "subject";

    @Override
    public Optional<Subject> findById(Integer id) {
        log.debug("findById: searches for a Subject with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new SubjectRowMapper(), id).stream()
                .findFirst();
    }

    @Override
    public int insert(Subject entity) {
        log.debug("insert: saves the Subject to the DB");

        String sql = """
                INSERT INTO subject(name, type)
                VALUES (?, ?);
                """;

        String type = entity.getType().name().toLowerCase();
        Integer result = util.handleDuplicateKeyException(() ->
                jdbcTemplate.update(sql, entity.getName(), type));

        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Subject with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Subject entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.warn("deleteAll: deletes all Subjects in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Subjects");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Subject> subjects = jdbcTemplate.query(sql, new SubjectRowMapper());

        return new PageImpl<>(subjects, pageable, count());
    }

    @Override
    public void insertAll(List<Subject> entities) {
        log.debug("insertAll: inserts multiple entities");

        String sql = """
                INSERT INTO subject(name, type) VALUES (?, ?);
                """;

        util.handleDuplicateKeyException(
                () -> jdbcTemplate.batchUpdate(sql, entities, entities.size(), (ps, argument) -> {
                    ps.setString(1, argument.getName());
                    ps.setString(2, argument.getType().name().toLowerCase());
                }));
    }

    @Override
    public Optional<Subject> findByNameAndSubjectType(String name, SubjectType subjectType) {
        log.debug("findByNameAndSubjectType: searches for a Subject {}, {}", name, subjectType);

        String sql = """
                SELECT * FROM subject
                WHERE name = ? AND type = ?;
                """;

        String type = subjectType.name().toLowerCase();

        return jdbcTemplate.query(sql, new SubjectRowMapper(), name, type)
                .stream().findFirst();
    }

}
