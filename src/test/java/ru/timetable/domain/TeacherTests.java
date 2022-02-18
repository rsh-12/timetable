package ru.timetable.domain;
/*
 * Date: 18.02.2022
 * Time: 5:13 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.timetable.domain.util.Gender;

public class TeacherTests {

    private final Teacher savedEntity = Teacher.builder()
            .lastName("Волков")
            .firstName("Михаил")
            .middleName("Иванович")
            .gender(Gender.MALE)
            .email("wolf@mail.ru")
            .phone("89435789349")
            .build();

    @Test
    public void equals_ShouldBeEqual() {
        // Same objects
        assertEquals(savedEntity, savedEntity);

        Teacher newEntity = Teacher.builder()
                .lastName("Волков")
                .firstName("Михаил")
                .middleName("Иванович")
                .gender(Gender.MALE)
                .build();

        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_ShouldNotBeEqual() {
        Teacher newEntity = Teacher.builder()
                .lastName("Иванова")
                .firstName("Лариса")
                .middleName("Александровна")
                .build();

        assertNotEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Teacher newEntity = Teacher.builder()
                .lastName("Волков")
                .firstName("Михаил")
                .middleName("Иванович")
                .gender(Gender.MALE)
                .build();

        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(newEntity.hashCode(), savedEntity.hashCode());
    }

}
