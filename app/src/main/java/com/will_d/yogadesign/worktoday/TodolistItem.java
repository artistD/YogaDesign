package com.will_d.yogadesign.worktoday;

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

    public TodolistItem() {

    }

    public TodolistItem(String no, int completeNum, String name, String nickName, boolean isGoalChecked, String goalSet, RelativeLayout rlTodolistLogDialog, EditText etTodolistLog, TextView tvTodolistLogCancel, TextView tvTodolistLogOk) {
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
    }

    public String getNo() {
        return no;
    }

    public int getCompleteNum() {
        return completeNum;
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
}

