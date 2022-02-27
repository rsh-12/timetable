package ru.timetable.domain.factory;
/*
 * Date: 27.02.2022
 * Time: 12:20 PM
 * */

import ru.timetable.domain.Period;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

public class CommonPeriodFactory extends PeriodFactory {

    @Override
    protected Period createPeriod(PeriodNum num) {
        return switch (num) {
            case FIRST -> new Period(num, Pair.of(8, 30, 9, 15), Pair.of(9, 20, 10, 5));
            case SECOND -> new Period(num, Pair.of(10, 15, 11, 0), Pair.of(11, 5, 11, 50));
            case THIRD -> new Period(num, Pair.of(12, 30, 13, 15), Pair.of(13, 20, 14, 5));
            case FOURTH -> new Period(num, Pair.of(14, 25, 15, 10), Pair.of(15, 15, 16, 0));
            case FIFTH -> new Period(num, Pair.of(16, 20, 17, 5), Pair.of(17, 10, 17, 55));
            case SIXTH -> new Period(num, Pair.of(18, 5, 18, 55), Pair.of(19, 0, 19, 40));
        };
    }

}
