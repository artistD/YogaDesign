package com.will_d.yogadesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import soup.neumorphism.NeumorphImageView;

public class WokrDataSetActivity extends AppCompatActivity {

    private EditText etName;
    private TextView tvNickname;
    private NeumorphImageView nivNickname;
    private TextView tvPhoto;
    private NeumorphImageView nivPhoto;
    private TextView tvWeeks;
    private NeumorphImageView nivWeeks;

    private Switch swGoal;
    private Switch swPreNotification;
    private Switch swLocalNotification;

    private RelativeLayout nicknameDialog;
    private RelativeLayout weeksDialog;
    private RelativeLayout goalDialog;
    private RelativeLayout preNotificationDialog;


    //nickname dialog
    private ImageView[] weeks = new ImageView[7];

    private TextView tvNicknameDialogSuccess;
    private TextView tvNicknameDialogCancel;

    private boolean[] weeksData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wokr_data_set);

        etName = findViewById(R.id.et_name);
        tvNickname = findViewById(R.id.tv_nickname);
        nivNickname = findViewById(R.id.niv_nickname);
        tvPhoto = findViewById(R.id.tv_photo);
        nivPhoto = findViewById(R.id.niv_photo);
        tvWeeks = findViewById(R.id.tv_weeks);
        nivWeeks = findViewById(R.id.niv_weeks);

        swGoal = findViewById(R.id.sw_goal);
        swPreNotification = findViewById(R.id.sw_preNotification);
        swLocalNotification = findViewById(R.id.sw_localNotification);

        nicknameDialog = findViewById(R.id.rl_nickname_dialog);
        weeksDialog = findViewById(R.id.rl_weeks_dialog);
        goalDialog = findViewById(R.id.rl_goal_dialog);
        preNotificationDialog = findViewById(R.id.rl_prenotification_dialog);


        //nickName dialog
        weeks[0] = findViewById(R.id.iv_weeks_mon);
        weeks[1] = findViewById(R.id.iv_weeks_tues);
        weeks[2] = findViewById(R.id.iv_weeks_wendnes);
        weeks[3] = findViewById(R.id.iv_weeks_thurs);
        weeks[4] = findViewById(R.id.iv_weeks_fri);
        weeks[5] = findViewById(R.id.iv_weeks_satur);
        weeks[6] = findViewById(R.id.iv_weeks_sun);

        tvNicknameDialogSuccess = findViewById(R.id.tv_nickname_dialog_success);
        tvNicknameDialogCancel = findViewById(R.id.tv_nickname_dialog_cancel);


        swGoal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        swPreNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        swLocalNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fragment_none, R.anim.activity_data_set_end);
    }

    public void clickClose(View view) {
        finish();
        overridePendingTransition(R.anim.fragment_none, R.anim.activity_data_set_end);
    }

    public void clickSave(View view) { //*************

    }

    public void clickSetNickName(View view) {
    }

    public void clickSetPhoto(View view) {

    }

    public void clickSetWeeks(View view) {

        boolean[] weeksData = new boolean[7];
        for (int i=0; i<weeks.length; i++) weeks[i].setTag("");
        for (int i=0; i<weeks.length; i++) weeksData[i] = false;
        Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);
        nicknameDialog.setVisibility(View.VISIBLE);

        tvNicknameDialogSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0; i<weeks.length; i++){
                    if (weeks[i].getTag().toString().equals("Checked")) weeksData[i] = true;
                }
                Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);

                for (int i=0; i<weeks.length; i++) weeks[i].setImageResource(R.drawable.ic_unchecked);
                nicknameDialog.setVisibility(View.INVISIBLE);

                WokrDataSetActivity.this.weeksData = weeksData;


            }
        });

        tvNicknameDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<weeks.length; i++) weeks[i].setImageResource(R.drawable.ic_unchecked);
                nicknameDialog.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void clickWeeksChecked(View view) {
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.ic_checked);
        imageView.setTag("Checked");
    }
}