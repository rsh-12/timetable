package ru.timetable.domain;
/*
 * Date: 06.02.2022
 * Time: 1:10 PM
 * */

import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private Integer id;

    @NonNull
    private String name;

    private String department;

    private Instant createdAt;

    private Instant updatedAt;

    public Group(@NonNull String name) {
        this.name = name;
    }

    public Group(@NonNull String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        if (!Objects.equals(id, group.id)) {
            return false;
        }
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

}
