package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import soup.neumorphism.NeumorphCardView;

public class LoginActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);

    }

    public void clickLogin(View view) {

    }

    public void clickFrofile(View view) {

    }
}