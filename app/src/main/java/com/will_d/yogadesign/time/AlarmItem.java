package com.will_d.yogadesign.time;

public class AlarmItem {

    String time;
    boolean[] weeks;

    public AlarmItem() {
    }

    public AlarmItem(String time, boolean[] weeks) {
        this.time = time;
        this.weeks = weeks;
    }

    public String getTime() {
        return time;
    }

    public boolean[] getWeeks() {
        return weeks;
    }
}
