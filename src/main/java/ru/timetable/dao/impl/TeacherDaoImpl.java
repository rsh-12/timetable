package ru.timetable.dao.impl;
/*
 * Date: 10.02.2022
 * Time: 10:47 AM
 * */

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.TeacherDao;
import ru.timetable.dao.mappers.TeacherRowMapper;
import ru.timetable.domain.Teacher;
import ru.timetable.util.ServiceUtil;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final ServiceUtil util;
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

        int result = 0;
        try {
            result = jdbcTemplate.update(sql,
                    entity.getLastName(),
                    entity.getFirstName(),
                    entity.getMiddleName(),
                    entity.getGender().name(),
                    entity.getEmail(),
                    entity.getPhone());
        } catch (DataIntegrityViolationException e) {
            log.error(e.getCause().getMessage());
        }

        return result;
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

    @Override
    public void insertAll(List<Teacher> entities) {
        log.debug("insertAll: inserts multiple entities");

        String sql = """
                INSERT INTO teacher(last_name, first_name, middle_name, gender,email, phone)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        util.handleDuplicateKeyException(
                () -> jdbcTemplate.batchUpdate(sql, entities, entities.size(), (ps, teacher) -> {
                    ps.setString(1, teacher.getLastName());
                    ps.setString(2, teacher.getFirstName());
                    ps.setString(3, teacher.getMiddleName());
                    ps.setString(4, teacher.getGender().name());
                    ps.setString(5, teacher.getEmail());
                    ps.setString(6, teacher.getPhone());
                }));
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        log.debug("findByEmail: searches for a Teacher with email={}", email);

        String sql = """
                SELECT * FROM %s
                WHERE email = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new TeacherRowMapper(), email)
                .stream().findFirst();
    }

    @Override
    public Optional<Teacher> findByPhone(String phone) {
        log.debug("findByEmail: searches for a Teacher with phone={}", phone);

        String sql = """
                SELECT * FROM %s
                WHERE phone = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new TeacherRowMapper(), phone)
                .stream().findFirst();
    }

}
