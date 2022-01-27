package ru.timetable.dao.impl;
/*
 * Date: 27.01.2022
 * Time: 6:49 AM
 * */

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.DayDao;
import ru.timetable.domain.Day;

@Slf4j
@Repository
public class DayDaoImpl implements DayDao {

    @Override
    public Optional<Day> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public int insert(Day day) {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
