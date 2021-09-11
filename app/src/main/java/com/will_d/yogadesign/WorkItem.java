package com.will_d.yogadesign;

import androidx.dynamicanimation.animation.SpringAnimation;

public class WorkItem {

    private String imgUrl;
    private String property;

    private boolean isgoal;
    private boolean ispreNotification;
    private boolean islocationNotification;

    private String explanation;
    private String addExplanation;

    private boolean[] isweeks = new boolean[7];

    public WorkItem() {

    }

    public WorkItem(String imgUrl, String property, boolean isgoal, boolean ispreNotification, boolean islocationNotification, String explanation, String addExplanation, boolean[] isweeks) {
        this.imgUrl = imgUrl;
        this.property = property;
        this.isgoal = isgoal;
        this.ispreNotification = ispreNotification;
        this.islocationNotification = islocationNotification;
        this.explanation = explanation;
        this.addExplanation = addExplanation;
        this.isweeks = isweeks;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getProperty() {
        return property;
    }

    public boolean getIsgoal() {
        return isgoal;
    }

    public boolean getIspreNotification() {
        return ispreNotification;
    }

    public boolean getIslocationNotification() {
        return islocationNotification;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getAddExplanation() {
        return addExplanation;
    }

    public boolean[] getIsweeks() {
        return isweeks;
    }
}


