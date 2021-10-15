package com.will_d.yogadesign.service;

import android.net.Uri;

import androidx.cardview.widget.CardView;

import com.will_d.yogadesign.worktoday.item.WorkItem;

import java.util.ArrayList;

public class Global {

    //dataSetActivity에서 데이터 판별할때 쓰는 것임
    public static ArrayList<WorkItem> workItems;
    //********

    //수정할때 쓰는 데이터 ******
    public static boolean isworkitemModifyChcecked = false;
    public static String no; //이거 절대 건드리면 안됨 수정할떄만 건드리는거임
    public static boolean isModifySave = false;
    //************************************************************


    //스위치 동기를 맞출때 쓰는것임 workTodayAdapter 과 itemAdapter간 데이터 교환일것임*********************************************************동기 문제가 아님
    public static boolean isSwitchEnd=false;
    public static boolean isDeletEnd=false;
    ////************************************************************

    //todolist와 workitem 동기를 위한 변수
    public static ArrayList<String> workItemIndextNo;


    public static boolean isFirst = false;

    public static String myRealImgUrl;
    public static Uri myImgUri;
    public static String myNickName;
    public static String myStateMsg;




}
