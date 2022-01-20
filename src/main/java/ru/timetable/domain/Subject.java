package ru.timetable.domain;
/*
 * Date: 20.01.2022
 * Time: 6:20 AM
 * */

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
public class Subject {

    @Id
    private Integer id;

    private String name;

    // lecture or practice
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
        this.type = "lecture";
    }

    public Subject(String name, String type) {
        this.name = name;
        this.type = type;
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
