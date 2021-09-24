package com.will_d.yogadesign;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RetrofitService {

    @Multipart
    @POST("/YogaDesign2/workitem/WorkItemInsertDB.php")
    Call<String> WorkItemPostDataToServer(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

    //서버에서 데이터를 json으로 받아 자동 파싱해서 ArrayList에 바로 넣어주는 기능 메소드
    @FormUrlEncoded
    @POST("/YogaDesign2/workitem/WorkItemLoadDB.php")
    Call<String> WorkItemLoadDataFromServer(@Field("id") String id);

    //서버에서 데이터를
    @Multipart
    @POST("/YogaDesign2/member/meberInsertDB.php")
    Call<String> memberPostDataToServer(@PartMap Map<String, String> datapart, @Part MultipartBody.Part filePart);

    //서버에서 데이터를 json으로 받아 자동 파싱해서 ArrayList에 바로 넣어주는 기능 메소드
    @GET("/YogaDesign2/member/memberLoadDB.php")
    Call<String> memberLoadDataFromServer();

    @FormUrlEncoded
    @POST("/YogaDesign2/todolist/TodolistLoadDB.php") //사실은 인설트 디비
    Call<String> TodolistLoadDataFromServer(@Field("id") String id);

    //스위치 끄는 기능임
    @GET("/YogaDesign2/workitem/WorkItemSwitchInsertDB.php")
    Call<String> WorkItemSwitchOnOffGetDataDB(@Query("isItemOnOff") boolean isItemOnOff, @Query("no") String no);

    @GET("/YogaDesign2/workitem/WorkItemCounterInsertDB.php")
    Call<String> WorkItemCounterInsertDataDB(@Query("Completenum") String counterNum, @Query("no") String no);

    @GET("/YogaDesign2/workitem/workitemTodolistBooleanStateInsertDB.php")
    Call<String> WorkItemTodolistBooleanStateInsertDB(@Query("todoistBooleanState") String todoistBooleanState, @Query("no") String no);

    @GET("/YogaDesign2/workitem/insertWorkitemTodolistBooleanStateInitDB.php")
    Call<String> insertWorkitemTodolistBooleanStateInitDB(@Query("todoistBooleanStateInit") String todoistBooleanStateInit);

    @GET("/YogaDesign2/workitem/workitemDeleteDB.php")
    Call<String> workitemDeleteDB(@Query("no") String no);

    @GET("/YogaDesign2/workitem/WorkItemModifyDataLoadDB.php")
    Call<String> WorkItemModifyDataLoadDB(@Query("no") String no);

//***************
    @GET("/YogaDesign2/workitem/workItemFromPositionChangeDB.php")
    Call<String> workItemFromPositionChangeDB(@Query("noFrom") String noFrom, @Query("noTo") String noTo);

    @GET("/YogaDesign2/workitem/workItemToPositionChangeDB.php")
    Call<String> workItemToPositionChangeDB(@Query("noFrom") String noFrom, @Query("noTo") String noTo);
    //********************** 이것들 일단보류


    @GET("/YogaDesign2/workitem/workItemOnedayUpdateDB.php")
    Call<String> workItemOnedayUpdateDB(@Query("off") boolean off);

    @Multipart
    @POST("/YogaDesign2/workitem/workTodayModifyUpdateToServer.php")
    Call<String> workTodayModifyUpdateToServer(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

    @GET("/YogaDesign2/workitem/workItemPublicUpdate.php")
    Call<String> workItemPublicUpdate(@Query("isPublic") boolean isPublic);



}
