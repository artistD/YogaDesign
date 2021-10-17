

package com.will_d.yogadesign.worktoday.item;

public class LogItem {

    private String date;
    private String log;
    private String time;


    public LogItem() {
    }

    public LogItem(String date, String log, String time) {
        this.date = date;
        this.log = log;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getLog() {
        return log;
    }

    public String getTime(){ return time; }
}
