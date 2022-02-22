package ru.timetable.dao;

import java.util.List;
import java.util.Optional;
import ru.timetable.domain.Audience;

public interface AudienceDao extends CrudDao<Audience> {

    Optional<Audience> findByNumber(String number);

    int[][] insertAll(List<Audience> audienceList);

}
