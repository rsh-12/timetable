package ru.timetable.domain;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import ru.timetable.domain.util.Weekday;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Day {

    @Id
    private Integer id;

    @NonNull
    private Weekday name;

    public Day(@NonNull Weekday name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Day day = (Day) o;

        if (!Objects.equals(id, day.id)) {
            return false;
        }
        return name == day.name;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Day{" +
                "name=" + name +
                '}';
    }

}
