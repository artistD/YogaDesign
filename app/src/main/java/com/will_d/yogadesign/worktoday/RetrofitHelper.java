package com.will_d.yogadesign.worktoday;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    //기본 서버 주소
    public static String baseUrl="http://willd88.dothome.co.kr/";

    //1. JSON Converter Retrofit
    public static Retrofit getRetrofitInstanceGson(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit;
    }

    //2. Scalars Converter Retrofit
    public static Retrofit getRetrofitScalars(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit;
    }

}
