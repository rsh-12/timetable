package ru.timetable.domain;
/*
 * Date: 08.02.2022
 * Time: 8:26 AM
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
import ru.timetable.domain.util.Gender;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    private Integer id;

    @NonNull
    private String lastName;

    @NonNull
    private String firstName;

    @NonNull
    private String middleName;

    private Gender gender;

    private String email;

    private String phone;

    private Instant createdAt;

    private Instant updatedAt;

    public Teacher(
            @NonNull String lastName,
            @NonNull String firstName,
            @NonNull String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Teacher teacher = (Teacher) o;

        if (!Objects.equals(id, teacher.id)) {
            return false;
        }
        if (!lastName.equals(teacher.lastName)) {
            return false;
        }
        if (!firstName.equals(teacher.firstName)) {
            return false;
        }
        if (!middleName.equals(teacher.middleName)) {
            return false;
        }
        return gender == teacher.gender;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
