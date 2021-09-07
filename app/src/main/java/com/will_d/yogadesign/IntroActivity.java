package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        iv = findViewById(R.id.iv);

        Glide.with(this).load(R.drawable.bg_intro01).into(iv);
    }

    public void clickBtn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}