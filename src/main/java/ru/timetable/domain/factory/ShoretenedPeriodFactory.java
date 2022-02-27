package ru.timetable.domain.factory;
/*
 * Date: 27.02.2022
 * Time: 12:20 PM
 * */

import ru.timetable.domain.Period;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

public class ShoretenedPeriodFactory extends PeriodFactory {

    @Override
    protected Period createPeriod(PeriodNum num) {
        return switch (num) {
            case FIRST -> new Period(num, Pair.of(8, 30, 9, 15), Pair.of(9, 20, 10, 5));
            case SECOND -> new Period(num, Pair.of(10, 15, 11, 0), Pair.of(11, 5, 11, 50));
            case THIRD -> new Period(num, Pair.of(12, 20, 13, 5), Pair.of(13, 10, 13, 55));
            case FOURTH -> new Period(num, Pair.of(14, 5, 14, 50), Pair.of(14, 55, 15, 40));
            case FIFTH -> new Period(num, Pair.of(15, 50, 16, 35), Pair.of(16, 40, 17, 25));
            case SIXTH -> new Period(num, Pair.of(17, 35, 18, 20), Pair.of(18, 25, 19, 10));
        };
    }

}
