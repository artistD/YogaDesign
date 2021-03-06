package com.will_d.neumorphism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.alterac.blurkit.BlurLayout;
import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {


    NeumorphButton neumorphButton;


    BlurLayout blurLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            String s = "05";
            int a = Integer.parseInt(s);
            Toast.makeText(this, a + "", Toast.LENGTH_SHORT).show();

            ArrayList<String> dd = new ArrayList<>();
            Log.i("arraysize", dd.size() +"");
            Gson gson = new Gson();
            String jsonStr = gson.toJson(dd);



            String[] aa = gson.fromJson(jsonStr, String[].class);
            ArrayList<String> ss = new ArrayList<String>(Arrays.asList(aa));


            Log.i("jsonStr", jsonStr);

            dd.add("a");
            dd.add("b");
            Log.i("Tag", dd.get(0));
            Log.i("Tag", dd.get(1));

            dd.remove("a");

            Log.i("Remove", dd.get(0));



        blurLayout = findViewById(R.id.blurlayout);
        neumorphButton = findViewById(R.id.nmp_button);

        neumorphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "dqqwtgq", Toast.LENGTH_SHORT).show();
            }
        });

        neumorphButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Long clickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        super.onStop();
        blurLayout.pauseBlur();
    }

    public void clickBtn(View view) {
        Toast.makeText(this, "qwfqgwe", Toast.LENGTH_SHORT).show();
    }
}