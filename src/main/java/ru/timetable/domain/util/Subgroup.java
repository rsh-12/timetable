package ru.timetable.domain.util;

public enum Subgroup {
    ALL(0),
    FIRST(1),
    SECOND(2);

    private final int subgroup;

    Subgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public int getAsInt() {
        return subgroup;
    }

}
