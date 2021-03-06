package ru.timetable.domain;
/*
 * Date: 21.01.2022
 * Time: 7:11 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AudienceTests {

    private final Audience savedEntity = new Audience("114");

    @Test
    public void equals_ShouldBeEqual() {
        // same objects
        assertEquals(savedEntity, savedEntity);

        Audience newEntity = new Audience("114");
        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_ShouldNotBeEqual() {
        Audience newEntity = new Audience("Gym");
        assertNotEquals(newEntity, savedEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Audience newEntity = new Audience("114");
        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(newEntity.hashCode(), savedEntity.hashCode());
    }

}
