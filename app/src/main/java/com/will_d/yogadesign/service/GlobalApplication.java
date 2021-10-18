package com.will_d.yogadesign.service;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "84e5e1776cc284bc3284e957ceef57e7");
    }
}
