package com.will_d.yogadesign;

import androidx.dynamicanimation.animation.SpringAnimation;

public class WorkItem {

    private String imgUrl;
    private String property;

    private boolean goal;
    private boolean preNotification;
    private boolean locationNotification;

    private String explanation;
    private String addExplanation;

    private String weekMon;
    private String weekTues;
    private String weekWendnes;
    private String weekThurs;
    private String weekFri;
    private String weekSatur;
    private String weekSun;

    public WorkItem() {


    }

    public WorkItem(String imgUrl, String property, boolean goal, boolean preNotification, boolean locationNotification,
                    String explanation, String addExplanation, String weekMon, String weekTues, String weekWendnes, String weekThurs, String weekFri, String weekSatur, String weekSun) {


        this.imgUrl = imgUrl;
        this.property = property;

        this.goal = goal;
        this.preNotification = preNotification;
        this.locationNotification = locationNotification;

        this.explanation = explanation;
        this.addExplanation = addExplanation;

        this.weekMon = weekMon;
        this.weekTues = weekTues;
        this.weekWendnes = weekWendnes;
        this.weekThurs = weekThurs;
        this.weekFri = weekFri;
        this.weekSatur = weekSatur;
        this.weekSun = weekSun;
    }
}
