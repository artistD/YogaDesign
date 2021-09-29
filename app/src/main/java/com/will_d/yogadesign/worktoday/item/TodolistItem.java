package com.will_d.yogadesign.worktoday.item;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TodolistItem {
    private String no;
    private int completeNum;
    private String name;
    private String nickName;

    private boolean isGoalChecked;
    private String goalSet;

    private RelativeLayout rlTodolistLogDialog;
    private EditText etTodolistLog;
    private TextView tvTodolistLogCancel;
    private TextView tvTodolistLogOk;

    private boolean[] todolistBooleanState;

    private boolean[] isDayOrTodaySelected;

    private boolean isLogModify;


    public TodolistItem() {

    }

    public TodolistItem(String no, int completeNum, String name, String nickName, boolean isGoalChecked, String goalSet, RelativeLayout rlTodolistLogDialog, EditText etTodolistLog, TextView tvTodolistLogCancel, TextView tvTodolistLogOk, boolean[] todolistBooleanState, boolean[] isDayOrTodaySelected, boolean isLogModify) {
        this.no = no;
        this.completeNum = completeNum;
        this.name = name;
        this.nickName = nickName;
        this.isGoalChecked = isGoalChecked;
        this.goalSet = goalSet;
        this.rlTodolistLogDialog = rlTodolistLogDialog;
        this.etTodolistLog = etTodolistLog;
        this.tvTodolistLogCancel = tvTodolistLogCancel;
        this.tvTodolistLogOk = tvTodolistLogOk;
        this.todolistBooleanState = todolistBooleanState;
        this.isDayOrTodaySelected = isDayOrTodaySelected;
        this.isLogModify = isLogModify;

    }

    public String getNo() {
        return no;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean getIsGoalChecked() {
        return isGoalChecked;
    }

    public String getGoalSet() {
        return goalSet;
    }

    public RelativeLayout getRlTodolistLogDialog() {
        return rlTodolistLogDialog;
    }

    public EditText getEtTodolistLog() {
        return etTodolistLog;
    }

    public TextView getTvTodolistLogCancel() {
        return tvTodolistLogCancel;
    }

    public TextView getTvTodolistLogOk() {
        return tvTodolistLogOk;
    }

    public boolean[] getTodolistBooleanState() {
        return todolistBooleanState;
    }

    public boolean[] getIsDayOrTodaySelected() { return isDayOrTodaySelected; }

    public boolean getIsLogModify() { return isLogModify; }

}

