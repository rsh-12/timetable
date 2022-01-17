package ru.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.timetable.domain.Day;

@Repository
public interface DayRepository extends CrudRepository<Day, Integer> {

}
