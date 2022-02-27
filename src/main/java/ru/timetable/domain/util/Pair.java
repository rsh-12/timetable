package ru.timetable.domain.util;
/*
 * Date: 16.01.2022
 * Time: 11:30 AM
 * */

import java.time.LocalTime;

/**
 * Represents the time of one half of the lesson, includes the start and end times
 */
public record Pair(String start, String end) {

    public static Pair of(LocalTime start, LocalTime end) {
        return new Pair(start.toString(), end.toString());
    }

    public static Pair of(int hour1, int minute1, int hour2, int minute2) {
        LocalTime firstHalf = LocalTime.of(hour1, minute1);
        LocalTime secondHalf = LocalTime.of(hour2, minute2);

        return new Pair(firstHalf.toString(), secondHalf.toString());
    }

}
