package ru.timetable.domain.factory;
/*
 * Date: 27.02.2022
 * Time: 12:17 PM
 * */

import ru.timetable.domain.Period;
import ru.timetable.domain.util.PeriodNum;

public abstract class PeriodFactory {

    protected abstract Period createPeriod(PeriodNum periodNum);

    public Period getPeriod(PeriodNum periodNum) {
        return createPeriod(periodNum);
    }

}
