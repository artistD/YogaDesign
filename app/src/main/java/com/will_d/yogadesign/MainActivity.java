package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.ReferenceQueue;

import io.alterac.blurkit.BlurLayout;
import jp.wasabeef.glide.transformations.BlurTransformation;
import soup.neumorphism.NeumorphCardView;

//프로젝트 하면서 주의 사항
//1. 접근제한자 [getter, setter : 미리 추가하지말고 필요할때마다 추가]
//2. 패키지 나눠서 할것
//3. Acitvity Theme 나눠서 사용할것, Fragment 적극적으로 활용하자
//4. 함수사용 적극적으로 할것

public class MainActivity extends AppCompatActivity {

    private ImageView ivWorkShop;
    private ImageView ivSet01;
    private ImageView ivSet02;
    private ImageView ivStatistical;
    private ImageView ivAlarm;
    private ImageView ivSquare;
    private ImageView ivTraffic;

    private NeumorphCardView cdWork;
    private NeumorphCardView cdAlarm;

    private BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blurLayout = findViewById(R.id.blurlayout);
        cdWork = findViewById(R.id.cd_work);
        cdAlarm= findViewById(R.id.cd_alarm);

        ivWorkShop = findViewById(R.id.iv_workshop);
        ivStatistical =findViewById(R.id.iv_statistical);
        ivAlarm = findViewById(R.id.iv_alarm);
        ivSquare = findViewById(R.id.iv_square);
        ivTraffic = findViewById(R.id.iv_traffic);


        Glide.with(this).load(R.drawable.bg_maincontents01).override(248, 248).into(ivWorkShop);
        Glide.with(this).load(R.drawable.bg_maincontents03).override(148, 148).into(ivStatistical);
        Glide.with(this).load(R.drawable.bg_maincontents04).override(148, 148).into(ivAlarm);
        Glide.with(this).load(R.drawable.bg_maincontents05).override(148, 148).into(ivSquare);
        Glide.with(this).load(R.drawable.bg_maincontents06).override(148, 148).into(ivTraffic);


    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
        Log.i("TAG", "Test");
    }


    @Override
    protected void onStop() {
        super.onStop();
        blurLayout.pauseBlur();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_vertical_none, R.anim.activity_horizontal_left_end);
    }



    public void clickGotoWorkShopActivity(View view) {
        onBackPressed();
    }

    public void clickGotoSetActivity(View view) {
        startActivity(new Intent(this, SetActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);
    }

    public void clickGotoStatisticalActivity(View view) {
        startActivity(new Intent(this, StatisticalActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);
    }

    public void clickGotoAlarmActivity(View view) {
        startActivity(new Intent(this, AlarmActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);
    }

    public void clickGotoSquareActivity(View view) {
        startActivity(new Intent(this, SquareActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);
    }

    public void clickGotoTrafficActivity(View view) {
        startActivity(new Intent(this, TrafficActivity.class));
        overridePendingTransition(R.anim.activity_horizontal_right, R.anim.activity_vertical_none);
    }


}