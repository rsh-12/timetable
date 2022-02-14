package ru.timetable.dao.impl;
/*
 * Date: 10.02.2022
 * Time: 10:47 AM
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
import ru.timetable.dao.TeacherDao;
import ru.timetable.dao.mappers.TeacherRowMapper;
import ru.timetable.domain.Teacher;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "teacher";

    @Override
    public Optional<Teacher> findById(Integer id) {
        log.debug("findById: searches for a Teacher with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new TeacherRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Teacher entity) {
        log.debug("insert: saves the Teacher to the DB");

        String sql = """
                INSERT INTO teacher(last_name, first_name, middle_name, gender, email, phone)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try {
            return jdbcTemplate.update(sql,
                    entity.getLastName(),
                    entity.getFirstName(),
                    entity.getMiddleName(),
                    entity.getGender().name(),
                    entity.getEmail(),
                    entity.getPhone());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Teacher with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Teacher entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Teachers in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Teachers");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Teacher> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Teacher> teachers = jdbcTemplate.query(sql, new TeacherRowMapper());

        return new PageImpl<>(teachers, pageable, count());

    }

}
