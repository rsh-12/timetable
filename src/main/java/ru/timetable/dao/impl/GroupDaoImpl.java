package ru.timetable.dao.impl;
/*
 * Date: 06.02.2022
 * Time: 1:24 PM
 * */

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.GroupDao;
import ru.timetable.dao.mappers.GroupRowMapper;
import ru.timetable.domain.Group;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Group> findById(Integer id) {
        log.debug("findById: searches for a Group with id={}", id);

        String sql = """
                SELECT * FROM student_group
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new GroupRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Group entity) {
        log.debug("insert: saves the Group to the DB");

        String sql = """
                INSERT INTO student_group(name, department)
                VALUES (?, ?);
                """;

        try {
            return jdbcTemplate.update(sql, entity.getName(), entity.getDepartment());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void delete(Group entity) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
