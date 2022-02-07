package ru.timetable.domain;
/*
 * Date: 31.01.2022
 * Time: 6:19 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

public class PeriodTests {

    private final Period savedEntity = Period.builder()
            .periodNum(PeriodNum.FIRST)
            .firstHalf(new Pair("08:30", "09:15"))
            .secondHalf(new Pair("09:20", "10:05"))
            .build();

    @Test
    public void equals_ShouldBeEqual() {
        // same objects
        assertEquals(savedEntity, savedEntity);

        Period newEntity = Period.builder()
                .periodNum(PeriodNum.FIRST)
                .firstHalf(new Pair("08:30", "09:15"))
                .secondHalf(new Pair("09:20", "10:05"))
                .build();

        assertEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_PeriodNum_ShouldNotBeEqual() {
        Period newEntity = Period.builder()
                .periodNum(PeriodNum.SECOND)
                .firstHalf(new Pair("08:30", "09:15"))
                .secondHalf(new Pair("09:20", "10:05"))
                .build();

        assertNotEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_Pair_ShouldNotBeEqual() {
        Period newEntity = Period.builder()
                .periodNum(PeriodNum.FIRST)
                .firstHalf(new Pair("8:30", "9:15"))
                .secondHalf(new Pair("9:20", "10:05"))
                .build();

        assertNotEquals(savedEntity, newEntity);
    }

    @Test
    public void equals_Symmetric_ShouldBeEqual() {
        Period newEntity = Period.builder()
                .periodNum(PeriodNum.FIRST)
                .firstHalf(new Pair("08:30", "09:15"))
                .secondHalf(new Pair("09:20", "10:05"))
                .build();

        assertTrue(newEntity.equals(savedEntity) && savedEntity.equals(newEntity));
        assertEquals(savedEntity.hashCode(), newEntity.hashCode());
    }

}
