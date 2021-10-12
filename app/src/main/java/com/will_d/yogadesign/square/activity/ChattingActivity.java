package com.will_d.yogadesign.square.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.will_d.yogadesign.R;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        //todo: Intent를 여러마리 보내면 구분은 어떻게 하지????
        Intent intent = getIntent();
        String checkedId = intent.getStringExtra("checkedId");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
    }

    public void clickBack(View view) {
    }
}