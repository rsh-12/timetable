package ru.timetable.domain;
/*
 * Date: 06.02.2022
 * Time: 1:15 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupTests {

    private Group savedEntity;

    @BeforeEach
    void setUp() {
        savedEntity = new Group("ПО-316", "ОИТ");
    }

    @Test
    public void equals_ShouldBeEqual() {
        // same objects
        assertEquals(savedEntity, savedEntity);

        Group newEntity = new Group("ПО-316", "ОИТ");
        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_ShouldNotBeEqual() {
        Group newEntity = new Group("ПО-215", "ОИТ");
        assertNotEquals(newEntity, savedEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Group newEntity = new Group("ПО-316", "ОИТ");
        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(newEntity.hashCode(), savedEntity.hashCode());
    }

}
