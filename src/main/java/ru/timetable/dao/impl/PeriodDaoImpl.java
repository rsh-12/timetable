package ru.timetable.dao.impl;
/*
 * Date: 31.01.2022
 * Time: 6:39 AM
 * */

import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.PeriodDao;
import ru.timetable.domain.Period;

@Repository
public class PeriodDaoImpl implements PeriodDao {

    @Override
    public Optional<Period> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public int insert(Period period) {
        return 0;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void delete(Period period) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

}
