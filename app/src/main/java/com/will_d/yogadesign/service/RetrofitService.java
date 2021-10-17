package com.will_d.yogadesign.service;

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
    @GET("/YogaDesign2/workitem/workItemPositionSetLoadToDB.php")
    Call<String> workItemPositionSetLoadToDB(@Query("workItemIndetJsonStr") String workItemIndetJsonStr, @Query("workItemIndexSize") int workItemIndexSize);
    //********************** 이것들 일단보류


    @GET("/YogaDesign2/workitem/workItemOnedayUpdateDB.php")
    Call<String> workItemOnedayUpdateDB(@Query("off") boolean off);

    @Multipart
    @POST("/YogaDesign2/workitem/workTodayModifyUpdateToServer.php")
    Call<String> workTodayModifyUpdateToServer(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

    @GET("/YogaDesign2/workitem/workItemPrivateUpdateDB.php")
    Call<String> workItemPrivateUpdateDB(@Query("no") String no, @Query("isItemPrivate") boolean isItemPrivate);


    @FormUrlEncoded
    @POST("/YogaDesign2/todolist/todolistLogDataInsertDB.php")
    Call<String> todolistLogDataInsertDB(@Field("identifier") int identifier, @Field("workItemNo") String workItemNo, @Field("days") String days, @Field("log") String log, @Field("isLogModify") boolean isLogModify);

    @GET("/YogaDesign2/todolist/logLoadDataFromServer.php")
    Call<String> logLoadDataFromServer(@Query("workItemNo") String workItemNo);

    //칼렌다 데이터에 관한 레트로핏임
    @FormUrlEncoded
    @POST("/YogaDesign2/todolist/todolistCalendarInsertDB.php")
    Call<String> todolistCalendarInsertDB(@Field("identifier") int idnetifier, @Field("workItemNo") String workItemNo, @Field("days") String days);

    @GET("/YogaDesign2/todolist/calendarDataLoadFromServer.php")
    Call<String> calendarDataLoadFromServer(@Query("workItemNo") String workItemNo);


    @GET("/YogaDesign2/timer/timeDataInserOrUpdatetDB.php")
    Call<String> timeDataInserOrUpdatetDB(@Query("identifier") int identifier, @Query("workItemNo") String workItemNo, @Query("days") String days, @Query("time") String time, @Query("isTimeFirst") boolean isTimeFirst);


    @GET("/YogaDesign2/timer/timeDataGetDB.php")
    Call<String> timeDataGetDB(@Query("workItemNO") String workItemNO, @Query("days") String days);



    //private 모드 세팅하는곳임
    @GET("/YogaDesign2/member/userSetPrivateModeUpdateDB.php")
    Call<String> userSetPrivateModeUpdateDB(@Query("id") String workItemNO, @Query("days") String days);

    @FormUrlEncoded
    @POST("/YogaDesign2/member/userSetPrivateModeUpdateDB.php")
    Call<String> userSetPrivateModeUpdateDB(@Field("id") String id, @Field("isPublic") boolean isPrivate);

    @GET("/YogaDesign2/square/squareMemberLoadDB.php")
    Call<String> squareMemberLoadDB();


    @FormUrlEncoded
    @POST("/YogaDesign2/square/sqareMemverListLoadDB.php")
    Call<String> sqareMemverListLoadDB(@Field("id") String id);


    @Multipart
    @POST("/YogaDesign2/member/memberProfileUpdateDB.php")
    Call<String> memberProfileUpdateDB(@PartMap Map<String, String> datapart, @Part MultipartBody.Part filePart);


    @GET("/YogaDesign2/workitem/workItemTimeSumUpdate.php")
    Call<String> workItemTimeSumUpdate(@Query("no") String no, @Query("timeSum") String timeSum);


    @GET("/YogaDesign2/square/squareMemberFavoriteCounterUpdateDB.php")
    Call<String> squareMemberFavoriteCounterUpdateDB(@Query("myId")String myId, @Query("favoriteCheckedId") String favoriteCheckedId, @Query("isFavorite") boolean isFavorite, @Query("favoriteCheckedUserList") String favoriteCheckedUserList);

    @GET("/YogaDesign2/square/squareFavoiteNumLoadDB.php")
    Call<String> squareFavoiteNumLoadDB(@Query("userId") String userId);

    @Multipart
    @POST("/YogaDesign2/member/memberSendMessageInsertDB.php")
    Call<String> memberSendMessageInsertDB(@PartMap Map<String, String> datapart, @Part MultipartBody.Part filePart);


    @FormUrlEncoded
    @POST("/YogaDesign2/member/memberChattingMessageLoadToDB.php")
    Call<String> memberChattingMessageLoadToDB(@Field("checkedId") String checkedId);



    @GET("/YogaDesign2/member/memberMyMessageDeleteDB.php")
    Call<String> memberMyMessageDeleteDB(@Query("no") String no);

    @FormUrlEncoded
    @POST("/YogaDesign2/member/memberLoginStateUpdateDB.php")
    Call<String> memberLoginStateUpdateDB(@Field("myId") String myId);












}
