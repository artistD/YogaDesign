package com.will_d.yogadesign.worktoday;

import androidx.dynamicanimation.animation.SpringAnimation;

import com.google.gson.annotations.SerializedName;

public class WorkItem {

    private String no;

    @SerializedName("dstName")
    private String imgUrl;

    private String nickName;
    private String name;

    @SerializedName("isGoalChecked")
    private boolean isgoal;

    @SerializedName("goalSet")
    private String tvgoal;

    @SerializedName("isPreNotificationChecked")
    private boolean ispreNotification;

    @SerializedName("preNotificationTime")
    private String tvpreNotification;

    @SerializedName("isLocalNotificationChecked")
    private boolean islocationNotification;

    @SerializedName("placeName")
    private String tvlocalNotification;

    @SerializedName("weeksDataJsonStr")
    private boolean[] isweeks = new boolean[7];

    public WorkItem() {

    }


    public WorkItem(String no, String imgUrl, String nickName, String name, boolean isgoal, String tvgoal, boolean ispreNotification, String tvpreNotification, boolean islocationNotification, String tvlocalNotification, boolean[] isweeks) {
        this.no = no;
        this.imgUrl = imgUrl;
        this.nickName = nickName;
        this.name = name;
        this.isgoal = isgoal;
        this.tvgoal = tvgoal;
        this.ispreNotification = ispreNotification;
        this.tvpreNotification = tvpreNotification;
        this.islocationNotification = islocationNotification;
        this.tvlocalNotification = tvlocalNotification;
        this.isweeks = isweeks;
    }

    public String getNo() {
        return no;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public String getName() {
        return name;
    }

    public boolean getIsGoal() {
        return isgoal;
    }

    public String getTvgoal() {
        return tvgoal;
    }

    public boolean getIspreNotification() {
        return ispreNotification;
    }

    public String getTvpreNotification() {
        return tvpreNotification;
    }

    public boolean getIslocationNotification() {
        return islocationNotification;
    }

    public String getTvlocalNotification() {
        return tvlocalNotification;
    }

    public boolean[] getIsweeks() {
        return isweeks;
    }
}


