package ru.timetable.domain;
/*
 * Date: 16.01.2022
 * Time: 11:30 AM
 * */

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the time of one half of the lesson, includes the start and end times
 */
@Getter
@Setter
public class Pair<S extends String, E extends String> {

    private S start;
    private E end;

    public Pair(S start, E end) {
        this.start = start;
        this.end = end;
    }

}
