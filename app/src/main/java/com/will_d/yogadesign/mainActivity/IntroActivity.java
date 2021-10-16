package com.will_d.yogadesign.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
//        Global.myImgUri = Uri.parse(pref.getString("myImgToStringUri", ""));
        Global.myRealImgUrl = pref.getString("myImgRealPathUrl", "");

        iv = findViewById(R.id.iv);

        Glide.with(this).load(R.drawable.bg_intro01).into(iv);


        startActivity(new Intent(this, LoginActivity.class));
    }


}