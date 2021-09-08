package com.will_d.yogadesign;

import androidx.dynamicanimation.animation.SpringAnimation;

public class WorkItem {

    private String imgUrl;
    private String property;

    private int goalColor;
    private int preNotificationColor;
    private int locationNotificationColor;

    private String explanation;
    private String addExplanation;

    private int[] weeksColor = new int[7];

    public WorkItem() {

    }

    public WorkItem(String imgUrl, String property, int goalColor, int preNotificationColor, int locationNotificationColor, String explanation, String addExplanation, int[] weeksColor) {
        this.imgUrl = imgUrl;
        this.property = property;
        this.goalColor = goalColor;
        this.preNotificationColor = preNotificationColor;
        this.locationNotificationColor = locationNotificationColor;
        this.explanation = explanation;
        this.addExplanation = addExplanation;
        this.weeksColor = weeksColor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getProperty() {
        return property;
    }

    public int getGoalColor() {
        return goalColor;
    }

    public int getPreNotificationColor() {
        return preNotificationColor;
    }

    public int getLocationNotificationColor() {
        return locationNotificationColor;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getAddExplanation() {
        return addExplanation;
    }

    public int[] getWeeksColor() {
        return weeksColor;
    }
}



