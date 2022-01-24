package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Audience;

public interface AudienceDao {

    Optional<Audience> findById(Integer id);

    int insert(Audience audience);

    void deleteAll();

    Optional<Audience> findByNumber(String number);

    int count();

}
