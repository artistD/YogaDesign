package com.will_d.yogadesign;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {

    @Multipart
    @POST("/YogaDesign/insertDB.php")
    Call<String> postDataToServer(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);

    //서버에서 데이터를 json으로 받아 자동 파싱해서 ArrayList에 바로 넣어주는 기능 메소드
    @GET("/YogaDesign/loadDB.php")
    Call<String> loadDataFromServer();

    //서버에서 데이터를
    @Multipart
    @POST("/YogaDesign2/member/meberInsertDB.php")
    Call<String> memberPostDataToServer(@PartMap Map<String, String> datapart, @Part MultipartBody.Part filePart);
}
