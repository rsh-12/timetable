package ru.timetable.domain.util;

public enum PeriodNum {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6);

    private final int periodNum;

    PeriodNum(int i) {
        this.periodNum = i;
    }

    public int getAsInt() {
        return periodNum;
    }

    public static PeriodNum valueOf(int n) {
        for (PeriodNum periodNum : values()) {
            if (periodNum.periodNum == n) {
                return periodNum;
            }
        }
        throw new IllegalArgumentException("Invalid periodNum: " + n);
    }

}
