package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoginActivity extends AppCompatActivity {

    ImageView ivMark;
    ImageView ivKakao;
    ImageView ivGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivMark = findViewById(R.id.iv_mark);
        ivKakao = findViewById(R.id.iv_kakao);
        ivGoogle = findViewById(R.id.iv_google);


        Glide.with(this).load(R.drawable.bg_intro01).into(ivMark);
        Glide.with(this).load(R.drawable.kakao_login_medium_narrow).into(ivKakao);
        Glide.with(this).load(R.drawable.kakao_login_medium_narrow).into(ivGoogle);
    }

    public void clickLogin(View view) {

    }

    public void clickBtn(View view) {
        startActivity(new Intent(this, WorkShopActivity.class));
    }
}