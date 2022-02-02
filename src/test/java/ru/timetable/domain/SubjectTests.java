package ru.timetable.domain;
/*
 * Date: 02.02.2022
 * Time: 6:29 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.timetable.domain.util.SubjectType;

public class SubjectTests {

    private Subject savedEntity;

    @BeforeEach
    void setUp() {
        savedEntity = Subject.builder()
                .id(1)
                .name("УП")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    public void equals_ShouldBeEqual() {
        assertEquals(savedEntity, savedEntity);

        Subject newEntity = Subject.builder()
                .id(1)
                .name("УП")
                .type(SubjectType.LECTURE)
                .build();

        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_ShouldNotBeEqual() {
        Subject newEntity = Subject.builder()
                .id(1)
                .name("УП")
                .type(SubjectType.PRACTICE)
                .build();

        assertNotEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Subject newEntity = Subject.builder()
                .id(1)
                .name("УП")
                .type(SubjectType.LECTURE)
                .build();

        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(newEntity.hashCode(), savedEntity.hashCode());
    }

}
