package ru.timetable.dao.impl;
/*
 * Date: 06.02.2022
 * Time: 1:24 PM
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
import ru.timetable.dao.GroupDao;
import ru.timetable.dao.mappers.GroupRowMapper;
import ru.timetable.domain.Group;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "\"group\"";

    @Override
    public Optional<Group> findById(Integer id) {
        log.debug("findById: searches for a Group with id={}", id);

        String sql = """
                SELECT * FROM %s
                WHERE id = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new GroupRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Group entity) {
        log.debug("insert: saves the Group to the DB");

        String sql = """
                INSERT INTO %s(name, department)
                VALUES (?, ?);
                """.formatted(TABLE);

        try {
            return jdbcTemplate.update(sql, entity.getName(), entity.getDepartment());
        } catch (DuplicateKeyException e) {
            log.warn(e.getCause().getMessage());
            return 0;
        }
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById: tries to delete a Group with id={}", id);

        String sql = """
                DELETE FROM %s WHERE id = ?;
                """.formatted(TABLE);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Group entity) {
        if (entity != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll: deletes all Groups in the DB");

        //noinspection SqlWithoutWhere
        jdbcTemplate.update("DELETE FROM %s;".formatted(TABLE));
    }

    @Override
    public int count() {
        log.debug("count: returns the number of all Groups");

        String sql = """
                SELECT COUNT(*) AS total
                FROM %s;
                """.formatted(TABLE);

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

    @Override
    public Page<Group> findAll(Pageable pageable) {
        log.debug("findAll: returns entities by pageable: size={}, offset={}",
                pageable.getPageSize(), pageable.getOffset());

        // todo: implement sorting
        String sql = """
                SELECT *
                FROM %s
                LIMIT %d
                OFFSET %d
                """.formatted(TABLE, pageable.getPageSize(), pageable.getOffset());

        List<Group> groups = jdbcTemplate.query(sql, new GroupRowMapper());

        return new PageImpl<>(groups, pageable, count());
    }

    @Override
    @Transactional
    public void insertAll(List<Group> entities) {
    }

    @Override
    public Optional<Group> findByName(String name) {
        log.debug("findByName: searches for a Group {}", name);

        String sql = """
                SELECT * FROM %s
                WHERE name = ?;
                """.formatted(TABLE);

        return jdbcTemplate.query(sql, new GroupRowMapper(), name)
                .stream().findFirst();
    }

}
