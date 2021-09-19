package com.will_d.yogadesign.worktoday;

public class TodolistItem {
    private String name;
    private String nickName;

    private boolean isCheckBox1Visible;
    private boolean isCheckBox2Visible;
    private boolean isCheckBox3Visible;

    public TodolistItem() {

    }

    public TodolistItem(String name, String nickName, boolean isCheckBox1Visible, boolean isCheckBox2Visible, boolean isCheckBox3Visible) {
        this.name = name;
        this.nickName = nickName;
        this.isCheckBox1Visible = isCheckBox1Visible;
        this.isCheckBox2Visible = isCheckBox2Visible;
        this.isCheckBox3Visible = isCheckBox3Visible;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean isCheckBox1Visible() {
        return isCheckBox1Visible;
    }

    public boolean isCheckBox2Visible() {
        return isCheckBox2Visible;
    }

    public boolean isCheckBox3Visible() {
        return isCheckBox3Visible;
    }
}

