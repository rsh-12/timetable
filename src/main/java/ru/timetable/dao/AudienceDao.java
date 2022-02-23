package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Audience;

public interface AudienceDao extends CrudDao<Audience> {

    Optional<Audience> findByNumber(String number);

}
