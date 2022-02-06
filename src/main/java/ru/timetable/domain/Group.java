package ru.timetable.domain;
/*
 * Date: 06.02.2022
 * Time: 1:10 PM
 * */

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "student_group")
public class Group {

    @Id
    private Integer id;

    @NonNull
    private String name;

    private String department;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
        if (!name.equals(group.name)) {
            return false;
        }
        return Objects.equals(department, group.department);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (department != null ? department.hashCode() : 0);
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
