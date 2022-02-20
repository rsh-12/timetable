package ru.timetable.domain;
/*
 * Date: 20.01.2022
 * Time: 6:20 AM
 * */

import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import ru.timetable.domain.util.SubjectType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    private Integer id;

    @NonNull
    private String name;

    @Default
    private SubjectType type = SubjectType.LECTURE;

    private Instant createdAt;

    private Instant updatedAt;

    public Subject(@NonNull String name) {
        this.name = name;
        this.type = SubjectType.LECTURE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subject subject = (Subject) o;

        if (!Objects.equals(id, subject.id)) {
            return false;
        }
        if (!name.equals(subject.name)) {
            return false;
        }
        return type.equals(subject.type);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
