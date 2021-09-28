package com.will_d.yogadesign.worktoday.item;

public class LogItem {

    String date;
    String log;


    public LogItem() {
    }

    public LogItem(String date, String log) {
        this.date = date;
        this.log = log;
    }

    public String getDate() {
        return date;
    }

    public String getLog() {
        return log;
    }
}
