package ru.timetable.dao.impl;
/*
 * Date: 02.02.2022
 * Time: 6:41 AM
 * */

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.SubjectDao;
import ru.timetable.dao.util.SubjectRowMapper;
import ru.timetable.domain.Subject;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SubjectDaoImpl implements SubjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Subject> findById(Integer id) {
        log.debug("findById: searches for a Subject with id={}", id);

        String sql = """
                SELECT * FROM subject
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new SubjectRowMapper(), id).stream()
                .findFirst();
    }

    @Override
    public int insert(Subject subject) {
        return 0;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void delete(Subject subject) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
