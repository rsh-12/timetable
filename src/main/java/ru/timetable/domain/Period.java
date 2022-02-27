package ru.timetable.domain;
/*
 * Date: 19.01.2022
 * Time: 7:38 AM
 * */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;


/**
 * Represents the start and end time of the half of the lesson.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Period {

    @Id
    private Integer id;

    @NonNull
    private PeriodNum periodNum;

    @NonNull
    private Pair firstHalf;

    @NonNull
    private Pair secondHalf;

    public Period(@NonNull PeriodNum periodNum, @NonNull Pair firstHalf,
            @NonNull Pair secondHalf) {
        this.periodNum = periodNum;
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Period period = (Period) o;

        if (periodNum != period.periodNum) {
            return false;
        }
        if (!firstHalf.equals(period.firstHalf)) {
            return false;
        }
        return secondHalf.equals(period.secondHalf);
    }

    @Override
    public int hashCode() {
        int result = periodNum.hashCode();
        result = 31 * result + firstHalf.hashCode();
        result = 31 * result + secondHalf.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "periodNum=" + periodNum +
                ", firstHalf=" + firstHalf +
                ", secondHalf=" + secondHalf +
                '}';
    }

}
