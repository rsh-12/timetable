package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Period;
import ru.timetable.domain.util.PeriodNum;

public interface PeriodDao extends CrudDao<Period> {

    Optional<Period> findByPeriodNum(PeriodNum periodNum);

}
