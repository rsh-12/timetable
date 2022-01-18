package ru.timetable.domain;
/*
 * Date: 18.01.2022
 * Time: 8:25 AM
 * */

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Represents the classroom number. The classroom number can be either a number or a string. For
 * example 114 or 'VRK'
 */
@Getter
@Setter
public class Audience {

    @Id
    private Integer id;

    private String number;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Audience() {

    }

    public Audience(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Audience audience = (Audience) o;

        if (!Objects.equals(id, audience.id)) {
            return false;
        }
        return number.equals(audience.number);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + number.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "number='" + number + '\'' +
                '}';
    }

}
