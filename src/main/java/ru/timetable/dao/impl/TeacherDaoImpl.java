package ru.timetable.dao.impl;
/*
 * Date: 10.02.2022
 * Time: 10:47 AM
 * */

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.TeacherDao;
import ru.timetable.domain.Teacher;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;
    private final String TABLE = "teacher";

    @Override
    public Optional<Teacher> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public int insert(Teacher entity) {
        return 0;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void delete(Teacher entity) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
