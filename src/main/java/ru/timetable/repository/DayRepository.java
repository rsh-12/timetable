package ru.timetable.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.timetable.domain.Day;
import ru.timetable.domain.Weekday;

@Repository
public interface DayRepository extends CrudRepository<Day, Integer> {

    Optional<Day> findByName(Weekday name);

}
