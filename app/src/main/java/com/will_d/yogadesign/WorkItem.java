package com.will_d.yogadesign;

import androidx.dynamicanimation.animation.SpringAnimation;

public class WorkItem {

    private String imgUrl;
    private String nickName;

    private boolean isgoal;
    private boolean ispreNotification;
    private boolean islocationNotification;

    private String name;
    private String addExplanation;

    private boolean[] isweeks = new boolean[7];

    public WorkItem() {

    }

    public WorkItem(String imgUrl, String nickName, boolean isgoal, boolean ispreNotification, boolean islocationNotification, String name, String addExplanation, boolean[] isweeks) {
        this.imgUrl = imgUrl;
        this.nickName = nickName;
        this.isgoal = isgoal;
        this.ispreNotification = ispreNotification;
        this.islocationNotification = islocationNotification;
        this.name = name;
        this.addExplanation = addExplanation;
        this.isweeks = isweeks;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean getIsGoal() {
        return isgoal;
    }

    public boolean getIspreNotification() {
        return ispreNotification;
    }

    public boolean getIslocationNotification() {
        return islocationNotification;
    }

    public String getName() {
        return name;
    }

    public String getAddExplanation() {
        return addExplanation;
    }

    public boolean[] getIsweeks() {
        return isweeks;
    }
}


