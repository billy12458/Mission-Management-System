package com.cabbage.mms.Entity;

public enum TimeEnum {

    EARLY_BREAKFAST("07:00:00"),
    LATE_BREAKFAST("08:00:00"),
    EARLY_LUNCH("12:00:00"),
    LATE_LUNCH("13:00:00"),
    EARLY_SUPPER("17:00:00"),
    LATE_SUPPER("18:00:00");

    public String numberOfTime;

    public String getNumberOfTime() {
        return this.numberOfTime;
    }

    private TimeEnum(String numberOfTime) {
        this.numberOfTime = numberOfTime;
    }
}
