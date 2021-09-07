package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void clickGotoHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.activity_vertical_none, R.anim.activity_vertical_none);
    }

    public void clickBackPress(View view) {
        onBackPressed();
    }
}