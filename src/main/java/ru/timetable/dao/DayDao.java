package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Day;
import ru.timetable.domain.util.Weekday;

public interface DayDao extends CrudDao<Day> {

    Optional<Day> findByWeekday(Weekday day);

}
