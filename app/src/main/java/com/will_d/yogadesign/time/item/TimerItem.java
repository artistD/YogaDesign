package com.will_d.yogadesign.time.item;

public class TimerItem {
    private String workItemNo;
    private String nickName;

    public TimerItem(String workItemNo, String nickName) {
        this.workItemNo = workItemNo;
        this.nickName = nickName;
    }


    public String getWorkItemNo() {
        return workItemNo;
    }

    public String getNickName() {
        return nickName;
    }
}


