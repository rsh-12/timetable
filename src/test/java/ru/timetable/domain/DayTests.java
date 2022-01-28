package ru.timetable.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.timetable.domain.util.Weekday;


class DayTests {

    private Day savedEntity;

    @BeforeEach
    void setUp() {
        savedEntity = new Day(Weekday.MONDAY);
    }

    @Test
    public void equals_ShouldBeEqual() {
        // same objects
        assertEquals(savedEntity, savedEntity);

        Day newEntity = new Day(Weekday.MONDAY);
        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_ShouldNotBeEqual() {
        Day newEntity = new Day(Weekday.SUNDAY);
        assertNotEquals(newEntity, savedEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Day newEntity = new Day(Weekday.MONDAY);
        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(newEntity.hashCode(), savedEntity.hashCode());
    }

}