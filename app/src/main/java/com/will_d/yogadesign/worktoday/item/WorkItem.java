package com.will_d.yogadesign.worktoday.item;

import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private boolean isItemInOff;

    private int completeNum;

    private boolean[] isDayOrTodaySelected;


    //이친구들은 팝업 설정을 위한친구들임
    private RelativeLayout rlWorkitemDeleteDialog;
    private TextView tvWorkitemDeleteOK;
    private TextView tvWorkitemDeleteCancel;

    //이친구는 log와 time에 대해서 신규로 등록할거냐 아니면 업데이트를 할거냐에 대한 boolean값임
    public boolean isLogModify =false;
    public boolean isTimeFirst =false;

    private boolean isPrivate  =false;



    public WorkItem() {

    }

    public WorkItem(String no, String imgUrl, String nickName, String name, boolean isgoal, String tvgoal, boolean ispreNotification, String tvpreNotification, boolean islocationNotification, String tvlocalNotification, boolean[] isweeks, boolean isItemInOff, int completeNum, boolean[] isDayOrTodaySelected, RelativeLayout rlWorkitemDeleteDialog, TextView tvWorkitemDeleteOK, TextView tvWorkitemDeleteCancel, boolean isLogModify, boolean isTimeFirst, boolean isPrivate) {
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
        this.isItemInOff = isItemInOff;
        this.completeNum = completeNum;
        this.isDayOrTodaySelected = isDayOrTodaySelected;
        this.rlWorkitemDeleteDialog = rlWorkitemDeleteDialog;
        this.tvWorkitemDeleteOK = tvWorkitemDeleteOK;
        this.tvWorkitemDeleteCancel = tvWorkitemDeleteCancel;
        this.isLogModify = isLogModify;
        this.isTimeFirst = isTimeFirst;
        this.isPrivate = isPrivate;
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

    public boolean getIsItemInOff() { return isItemInOff; }

    public int getCompleteNum() { return completeNum; }

    public boolean[] getIsDayOrTodaySelected(){ return isDayOrTodaySelected; }

    public RelativeLayout getRlWorkitemDeleteDialog() { return rlWorkitemDeleteDialog; }

    public TextView getTvWorkitemDeleteOK() { return tvWorkitemDeleteOK; }

    public TextView getTvWorkitemDeleteCancel() { return tvWorkitemDeleteCancel; }

    public boolean getIsLogModify() {
        return isLogModify;
    }

    public boolean getIsTimeFirst() {
        return isTimeFirst;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
}


