package ru.timetable.domain;
/*
 * Date: 19.01.2022
 * Time: 7:38 AM
 * */

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Period {

    @Id
    private Integer id;

    // a number between 1 and 6
    private short periodNum;

    private Pair<String, String> firstHalf;

    private Pair<String, String> secondHalf;

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
        return Objects.equals(id, period.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) periodNum;
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
