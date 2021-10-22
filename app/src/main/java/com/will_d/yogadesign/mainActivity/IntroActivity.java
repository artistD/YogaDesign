package com.will_d.yogadesign.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.common.util.Utility;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;

public class IntroActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        Global.myNickName = pref.getString("myNickName","");
        Global.myStateMsg = pref.getString("myStateMsg", "");
        Global.myRealImgUrl = pref.getString("myImgRealPathUrl", "");

        iv = findViewById(R.id.iv);

        Glide.with(this).load(R.drawable.ic_yodadesign_brand_foreground).into(iv);

        Animation ani = AnimationUtils.loadAnimation(this, R.anim.activity_intro);
        iv.startAnimation(ani);

        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}