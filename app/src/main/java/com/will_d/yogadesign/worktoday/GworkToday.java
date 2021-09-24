package com.will_d.yogadesign.worktoday;

import com.will_d.yogadesign.worktoday.WorkItem;

import java.util.ArrayList;

public class GworkToday {

    //dataSetActivity에서 데이터 판별할때 쓰는 것임
    static ArrayList<WorkItem> workItems;
    //********

    //수정할때 쓰는 데이터 ******
    static boolean isworkitemModifyChcecked = false;
    static String no; //이거 절대 건드리면 안됨 수정할떄만 건드리는거임
    static boolean isModifySave = false;
    //************************************************************


    //스위치 동기를 맞출때 쓰는것임 workTodayAdapter 과 itemAdapter간 데이터 교환일것임*********************************************************동기 문제가 아님
    public static boolean isSwitchEnd=false;
    public static boolean isDeletEnd=false;
    ////************************************************************

}
