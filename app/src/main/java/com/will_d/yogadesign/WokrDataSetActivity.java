package com.will_d.yogadesign;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import soup.neumorphism.NeumorphImageView;

public class WokrDataSetActivity extends AppCompatActivity {

    private final int PERMISSION_EX_PHOTO =100;

    private EditText etName;
    private TextView tvNickname;
    private NeumorphImageView nivNickname;
    private TextView tvPhoto;
    private CircleImageView civPhoto;
    private NeumorphImageView nivPhoto;
    private TextView tvWeeks;
    private NeumorphImageView nivWeeks;

    private TextView tvGoal;
    private CardView mcdGoalFixed;
    private Switch swGoal;
    private TextView tvPreNotification;
    private CardView mcdPreNotificationFixed;
    private Switch swPreNotification;
    private TextView tvLocalNotifiacation;
    private CardView mcdLocalNotificationFixed;
    private Switch swLocalNotification;

    private RelativeLayout nicknameDialog;
    private RelativeLayout nicknameDialogdirectlyinput;
    private RelativeLayout weeksDialog;
    private RelativeLayout goalDialog;
    private RelativeLayout preNotificationDialog;
    private LinearLayout localNotificationDialog;


    //nickName dialog
    TextView[] nickNames = new TextView[15];
    private EditText etNickNameDialogDirectlyInput;
    private TextView tvNickNameDialogDirectlyInputOK;
    private TextView tvNickNameDialogDirectlyInputCancel;


    //Weeks dialog
    private ImageView[] weeks = new ImageView[7];
    private TextView tvWeeksDialogSuccess;
    private boolean isWeeksChecked= false;

    //goal dialog
    private NumberPicker numberPickerGoalNumber;
    private TextView tvGoalOk;
    private TextView tvGoalCancel;

    //preNotification dialog
    private TimePicker  timePickerPreNotification;
    private TextView tvPreNotificationOk;
    private TextView tvPreNotificationCancel;


    //localNotification dialog
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SupportMapFragment mapFragment;
    private EditText etLocalNotificationSearch;
    private NeumorphImageView nivLocalNotificationSearchComplete;
    private TextView tvLocalNotificationOk;
    private TextView tvLocalNotificationCancel;
    private EditText etLocalNotificationPlaceName;






    private ArrayList<WorkItem> workItems; //*********** 판별을 하기위한 아이템 객체
    //################################
    private String name="";
    private String nickName = "";
    private String imgPath="";
    private boolean[] weeksData = new boolean[7];
    private boolean isGoalChecked=false;
    private String goalSet="";
    private boolean isPreNotificationChecked=false;
    private String preNotificationTime="";
    private boolean isLocalNotificationChecked=false;
    private String placeName = "";
    private double latitude=0.0;
    private double longitude=0.0;
    //##############################




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wokr_data_set);

        etName = findViewById(R.id.et_name);
        tvNickname = findViewById(R.id.tv_nickname);
        nivNickname = findViewById(R.id.niv_nickname);
        tvPhoto = findViewById(R.id.tv_photo);
        civPhoto = findViewById(R.id.civ_photo);
        nivPhoto = findViewById(R.id.niv_photo);
        tvWeeks = findViewById(R.id.tv_weeks);
        nivWeeks = findViewById(R.id.niv_weeks);

        tvGoal = findViewById(R.id.tv_goal);
        mcdGoalFixed =findViewById(R.id.mcd_goal_fixed);
        swGoal = findViewById(R.id.sw_goal);
        tvPreNotification = findViewById(R.id.tv_preNotification);
        mcdPreNotificationFixed = findViewById(R.id.mcd_preNotification_fixed);
        swPreNotification = findViewById(R.id.sw_preNotification);
        tvLocalNotifiacation = findViewById(R.id.tv_localNotification);
        mcdLocalNotificationFixed = findViewById(R.id.mcd_locaNotification_fixed);
        swLocalNotification = findViewById(R.id.sw_localNotification);

        nicknameDialog = findViewById(R.id.rl_nickname_dialog);
        nicknameDialogdirectlyinput = findViewById(R.id.rl_nickname_dialog_directlyinput);
        weeksDialog = findViewById(R.id.rl_weeks_dialog);
        goalDialog = findViewById(R.id.rl_goal_dialog);
        preNotificationDialog = findViewById(R.id.rl_prenotification_dialog);
        localNotificationDialog = findViewById(R.id.rl_localNotification_dialog);

        //nickname dialog
        etNickNameDialogDirectlyInput = findViewById(R.id.et_nicknamedialog_directlyinput);
        tvNickNameDialogDirectlyInputOK = findViewById(R.id.tv_nicknamedialog_directlyinput_ok);
        tvNickNameDialogDirectlyInputCancel = findViewById(R.id.tv_nicknamedialog_directlyinput_cancel);

        //Gson 작업
        Intent intent =getIntent();
        String jsonStr = intent.getStringExtra("Workitems");
        Gson gson = new Gson();
        WorkItem[] workItem = gson.fromJson(jsonStr, WorkItem[].class);
        workItems = new ArrayList<>(Arrays.asList(workItem));

        //weeks dialog
        weeks[0] = findViewById(R.id.iv_weeks_mon);
        Log.i("TAG", "" + weeks[0]);
        weeks[1] = findViewById(R.id.iv_weeks_tues);
        weeks[2] = findViewById(R.id.iv_weeks_wendnes);
        weeks[3] = findViewById(R.id.iv_weeks_thurs);
        weeks[4] = findViewById(R.id.iv_weeks_fri);
        weeks[5] = findViewById(R.id.iv_weeks_satur);
        weeks[6] = findViewById(R.id.iv_weeks_sun);

        tvWeeksDialogSuccess = findViewById(R.id.tv_weeks_dialog_success);

        for (int i=0; i<weeksData.length; i++) {
            weeks[i].setTag(""+i);
            weeksData[i] = false;
        }

        //goal dialog
        numberPickerGoalNumber = findViewById(R.id.numberPicker_goal_number);
        tvGoalOk = findViewById(R.id.tv_goal_ok);
        tvGoalCancel = findViewById(R.id.tv_goal_cancel);

        numberPickerGoalNumber.setMinValue(1);
        numberPickerGoalNumber.setMaxValue(3);

        //preNotification dialog
        timePickerPreNotification = findViewById(R.id.timepicker_prenotification);
        timePickerPreNotification.setIs24HourView(true);
        tvPreNotificationOk= findViewById(R.id.tv_preNotification_ok);
        tvPreNotificationCancel = findViewById(R.id.tv_preNotification_dialog_cancel);

        //localNotification dialog
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.fragment_map);
        etLocalNotificationSearch = findViewById(R.id.et_localNotification_search);
        nivLocalNotificationSearchComplete = findViewById(R.id.niv_localNotification_searchcomplete);
        tvLocalNotificationOk = findViewById(R.id.tv_localNotification_ok);
        tvLocalNotificationCancel = findViewById(R.id.tv_localNotification_cancel);
        etLocalNotificationPlaceName = findViewById(R.id.et_LocalNotification_placename);

        //퍼미션 작업 수행
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, PERMISSION_EX_PHOTO);
        }




        swGoal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isGoalChecked = isChecked;
                    Log.i("TAG", isGoalChecked+"");
                    goalDialog.setVisibility(View.VISIBLE);

                    tvGoalOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String valStr = "하루에 " + numberPickerGoalNumber.getValue() + "번";
                            tvGoal.setText(valStr+"");
                            tvGoal.setTextColor(0xFF9999FF);
                            goalSet = valStr;
                            Log.i("TAG", goalSet);
                            goalDialog.setVisibility(View.INVISIBLE);
                            mcdGoalFixed.setVisibility(View.VISIBLE);
                        }
                    });

                    tvGoalCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goalDialog.setVisibility(View.INVISIBLE);
                            mcdGoalFixed.setVisibility(View.INVISIBLE);
                            swGoal.setChecked(false);
                            isGoalChecked = false;
                            Log.i("TAG", isGoalChecked + "");
                        }
                    });
                    Log.i("TAG", numberPickerGoalNumber.getValue() + "");

                }else {
                    tvGoal.setText("목표");
                    tvGoal.setTextColor(0xFF666666);
                    isGoalChecked = isChecked;
                    mcdGoalFixed.setVisibility(View.INVISIBLE);
                    Log.i("TAG", isGoalChecked + "");
                }


            }
        });


        swPreNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isPreNotificationChecked = isChecked;
                    preNotificationDialog.setVisibility(View.VISIBLE);
                    Log.i("TAG", isPreNotificationChecked + "");



                    tvPreNotificationOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String strTime = String.format("%02d:%02d", timePickerPreNotification.getHour(), timePickerPreNotification.getMinute());
                            tvPreNotification.setText(strTime);
                            tvPreNotification.setTextColor(0xFF9999FF);
                            preNotificationTime = strTime;
                            Log.i("TAG", preNotificationTime + "");
                            preNotificationDialog.setVisibility(View.INVISIBLE);
                            mcdPreNotificationFixed.setVisibility(View.VISIBLE);

                        }
                    });

                    tvPreNotificationCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swPreNotification.setChecked(false);
                            isPreNotificationChecked = false;
                            Log.i("TAG", isPreNotificationChecked + "");
                            preNotificationDialog.setVisibility(View.INVISIBLE);
                            mcdPreNotificationFixed.setVisibility(View.INVISIBLE);
                        }
                    });


                }else {
                    tvPreNotification.setText("미리알림");
                    tvPreNotification.setTextColor(0xFF666666);
                    isPreNotificationChecked = isChecked;
                    mcdPreNotificationFixed.setVisibility(View.INVISIBLE);
                    Log.i("TAG", isPreNotificationChecked + "");
                }

            }
        });

        swLocalNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isLocalNotificationChecked = isChecked;
                    mcdLocalNotificationFixed.setVisibility(View.VISIBLE);
                    localNotificationDialog.setVisibility(View.VISIBLE);
                    Log.i("TAG", isLocalNotificationChecked+"");

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng userLocation = new LatLng(37.560955, 127.034721);

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 20));

                            MarkerOptions marker = new MarkerOptions();
                            marker.position(userLocation);
                            marker.title("미래능력 개발 교육원");
                            marker.snippet("왕십리역에 있는 s/w교육원");

                            googleMap.addMarker(marker);

                        }
                    });

                    tvLocalNotificationOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            localNotificationDialog.setVisibility(View.INVISIBLE);
                            placeName = etLocalNotificationPlaceName.getText().toString();
                        }
                    });

                    tvLocalNotificationCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isLocalNotificationChecked = false;
                            swLocalNotification.setChecked(false);
                            mcdLocalNotificationFixed.setVisibility(View.INVISIBLE);
                            localNotificationDialog.setVisibility(View.INVISIBLE);
                        }
                    });


                }else {
                    isLocalNotificationChecked = isChecked;
                    mcdLocalNotificationFixed.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_EX_PHOTO && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부버장소 접근 허용", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "이미지 업로드 불가", Toast.LENGTH_SHORT).show();
        }
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

    public void clickSave(View view) { //********************************
        name = etName.getText().toString();
        Intent intent = getIntent();
        intent.putExtra("name", name);
        intent.putExtra("nickName", nickName);
        intent.putExtra("imgPath", imgPath);
        intent.putExtra("weeksData", weeksData);
        intent.putExtra("isGoalChecked", isGoalChecked);
        intent.putExtra("goalSet", goalSet);
        intent.putExtra("isPreNotificationChecked", isPreNotificationChecked);
        intent.putExtra("preNotificationTime", preNotificationTime);
        intent.putExtra("isLocalNotificationChecked", isLocalNotificationChecked);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("placeName", placeName);

        setResult(RESULT_OK, intent);





        //** 최종데이터 전송
        Log.i("Final", "name : " + name);
        Log.i("Final", "nickName : " + nickName);
        Log.i("Final", "imgPath : " + imgPath);
        Log.i("Final", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);
        Log.i("Final", "isGoalChecked : " + isGoalChecked);
        Log.i("Final", "goalSet : " + goalSet);
        Log.i("Final", "isPreNotificationChecked : " + isPreNotificationChecked);
        Log.i("Final", "preNotificationTime : " + preNotificationTime);
        Log.i("Final", "isLocalNotificationChecked : " + isLocalNotificationChecked);
        Log.i("Final", "preNotifiationTime : " + latitude);
        Log.i("Final", "longitude : " + longitude);
        Log.i("Final", "placename : " + placeName);

        WorkTodayDataToServer();

    }


    public void clickSetNickName(View view) {
        nicknameDialog.setVisibility(View.VISIBLE);
    }

    public void clickSetPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        intentActivityResultLauncher.launch(intent);
    }

    public void clickSetWeeks(View view) {
        Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);
        weeksDialog.setVisibility(View.VISIBLE);

        tvWeeksDialogSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);

                weeksDialog.setVisibility(View.INVISIBLE);

                StringBuffer buffer = new StringBuffer();
                if (weeksData[0]) buffer.append(" 월 ");
                if (weeksData[1]) buffer.append(" 화 ");
                if (weeksData[2]) buffer.append(" 수 ");
                if (weeksData[3]) buffer.append(" 목 ");
                if (weeksData[4]) buffer.append(" 금 ");
                if (weeksData[5]) buffer.append(" 토 ");
                if (weeksData[6]) buffer.append(" 일 ");

                String weeksStr = buffer.toString();
                String replaceFirst = weeksStr.replaceFirst(" ", "");
                if (replaceFirst.length()>0) {
                    tvWeeks.setText(replaceFirst);
                    tvWeeks.setTextColor(0xFF9999FF);
                }else {
                    tvWeeks.setText("날짜 선택");
                    tvWeeks.setTextColor(0xFF666666);
                }

            }
        });

    }

    public void clickNickNamePick(View view) {
        TextView tv = (TextView) view;
        for (int i=0; i<workItems.size(); i++){
            if (workItems.get(i).getNickName().equals(tv.getText().toString())){
                Toast.makeText(this, "별명이 중복될수는 없습니다!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        WokrDataSetActivity.this.nickName = tv.getText().toString();
        tvNickname.setText(nickName);
        tvNickname.setTextColor(0xFF9999FF);
        etNickNameDialogDirectlyInput.setText("");
        Log.i("TAG", nickName);
        nicknameDialog.setVisibility(View.INVISIBLE);


    }

    public void clickSetNickNameDialogDirectInput(View view) {
        nicknameDialogdirectlyinput.setVisibility(View.VISIBLE);
        tvNickNameDialogDirectlyInputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNickNameDialogDirectlyInput.getText().toString().length()>0){
                    for (int i=0; i<workItems.size(); i++){
                        if (workItems.get(i).getNickName().equals(etNickNameDialogDirectlyInput.getText().toString())){
                            Toast.makeText(WokrDataSetActivity.this, "별명이 중복될수는 없습니다!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    nickName = etNickNameDialogDirectlyInput.getText().toString();
                    etNickNameDialogDirectlyInput.setText(nickName);
                    tvNickname.setText(nickName);
                    tvNickname.setTextColor(0xFF9999FF);
                    nicknameDialogdirectlyinput.setVisibility(View.INVISIBLE);
                    nicknameDialog.setVisibility(View.INVISIBLE);
                    Log.i("TAG", nickName);

                }else {
                    Toast.makeText(WokrDataSetActivity.this, "글자를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", nickName);
                }

            }
        });

        tvNickNameDialogDirectlyInputCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<workItems.size(); i++){
                    if (workItems.get(i).getNickName().equals(etNickNameDialogDirectlyInput.getText().toString())){
                        etNickNameDialogDirectlyInput.setText("");
                        nicknameDialogdirectlyinput.setVisibility(View.INVISIBLE);
                        Log.i("TAG", nickName);
                        return;
                    }
                }
                if (etNickNameDialogDirectlyInput.getText().toString().length()>0){
                    nicknameDialogdirectlyinput.setVisibility(View.INVISIBLE);
                    Log.i("TAG", nickName);
                }else {
                    etNickNameDialogDirectlyInput.setText("");
                    nicknameDialogdirectlyinput.setVisibility(View.INVISIBLE);
                    Log.i("TAG", nickName);
                }
            }
        });

    }



    public void clickWeeksChecked(View view) {
        isWeeksChecked=!isWeeksChecked;
        ImageView imageView = (ImageView) view;
        if (isWeeksChecked){
            imageView.setImageResource(R.drawable.ic_checked);
            if (imageView.getTag().toString().equals("0")) weeksData[0] = true;
            else if (imageView.getTag().toString().equals("1")) weeksData[1] = true;
            else if (imageView.getTag().toString().equals("2")) weeksData[2] = true;
            else if (imageView.getTag().toString().equals("3")) weeksData[3] = true;
            else if (imageView.getTag().toString().equals("4")) weeksData[4] = true;
            else if (imageView.getTag().toString().equals("5")) weeksData[5] = true;
            else if (imageView.getTag().toString().equals("6")) weeksData[6] = true;

        }else {
            imageView.setImageResource(R.drawable.ic_unchecked);
            if (imageView.getTag().toString().equals("0")) weeksData[0] = false;
            else if (imageView.getTag().toString().equals("1")) weeksData[1] = false;
            else if (imageView.getTag().toString().equals("2")) weeksData[2] = false;
            else if (imageView.getTag().toString().equals("3")) weeksData[3] = false;
            else if (imageView.getTag().toString().equals("4")) weeksData[4] = false;
            else if (imageView.getTag().toString().equals("5")) weeksData[5] = false;
            else if (imageView.getTag().toString().equals("6")) weeksData[6] = false;
        }
    }//clickWeeksChecked method....

    public void clickGoalFixed(View view) {

        Log.i("TAG", isGoalChecked+"");
        goalDialog.setVisibility(View.VISIBLE);

        tvGoalOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valStr = "하루에 " + numberPickerGoalNumber.getValue() + "번";
                tvGoal.setText(valStr+"");
                tvGoal.setTextColor(0xFF9999FF);
                goalSet = valStr;
                Log.i("TAG", goalSet);
                goalDialog.setVisibility(View.INVISIBLE);
                mcdGoalFixed.setVisibility(View.VISIBLE);
            }
        });

        tvGoalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalDialog.setVisibility(View.INVISIBLE);
                Log.i("TAG", isGoalChecked + "");
            }
        });
        Log.i("TAG", numberPickerGoalNumber.getValue() + "");

    }


    //dialog 부분임
    public void clickPreNotificationFixed(View view) {
        preNotificationDialog.setVisibility(View.VISIBLE);
        Log.i("TAG", isPreNotificationChecked + "");

        tvPreNotificationOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTime = String.format("%02d:%02d", timePickerPreNotification.getHour(), timePickerPreNotification.getMinute());
                tvPreNotification.setText(strTime);
                tvPreNotification.setTextColor(0xFF9999FF);
                preNotificationTime = strTime;
                Log.i("TAG", preNotificationTime + "");
                preNotificationDialog.setVisibility(View.INVISIBLE);
                mcdPreNotificationFixed.setVisibility(View.VISIBLE);

            }
        });

        tvPreNotificationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preNotificationDialog.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void clickLocalNotificationFixed(View view) {

        mcdLocalNotificationFixed.setVisibility(View.VISIBLE);
        localNotificationDialog.setVisibility(View.VISIBLE);
        Log.i("TAG", isLocalNotificationChecked+"");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng userLocation = new LatLng(37.560955, 127.034721);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 20));

                MarkerOptions marker = new MarkerOptions();
                marker.position(userLocation);
                marker.title("미래능력 개발 교육원");
                marker.snippet("왕십리역에 있는 s/w교육원");

                googleMap.addMarker(marker);

            }
        });

        tvLocalNotificationOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localNotificationDialog.setVisibility(View.INVISIBLE);
                placeName = etLocalNotificationPlaceName.getText().toString();
            }
        });

        tvLocalNotificationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localNotificationDialog.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void WorkTodayDataToServer(){
        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        MultipartBody.Part filePart = null;
        String realImgagePath = getRealPathFromUri(Uri.parse(imgPath));
        if (realImgagePath!=null){
            File file = new File(realImgagePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);

            Map<String, String> dataPart = new HashMap<>();
            dataPart.put("name", name);
            dataPart.put("nickName", nickName);
//          dataPart.put("imgPath", imgPath);  //이미지는 절대경로로 보내주는거니까 안줘도됨.
            Gson gson = new Gson();
            String weeksDataJsonStr = gson.toJson(weeksData);
            dataPart.put("weeksDataJsonStr", weeksDataJsonStr);

            dataPart.put("isGoalChecked", String.valueOf(isGoalChecked));
            dataPart.put("goalSet", goalSet);
            dataPart.put("isPreNotificationChecked", String.valueOf(isPreNotificationChecked));
            dataPart.put("preNotificationTime", preNotificationTime);
            dataPart.put("isLocalNotificationChecked", String.valueOf(isLocalNotificationChecked));
            dataPart.put("latitude", String.valueOf(latitude));
            dataPart.put("longitude", String.valueOf(longitude));
            dataPart.put("placeName", placeName);

            Call<String> call = retrofitService.postDataToServer(dataPart, filePart);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("retrofit", response.body());
                    finish();
                    overridePendingTransition(R.anim.fragment_none, R.anim.activity_data_set_end);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(WokrDataSetActivity.this, "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                    Log.i("retrofit", "ERROR : " + t.getMessage());

                }
            });

        }


    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                Uri uri = intent.getData();
                if (uri != null){
                    tvPhoto.setVisibility(View.INVISIBLE);
                    civPhoto.setVisibility(View.VISIBLE);
                    Glide.with(WokrDataSetActivity.this).load(uri).into(civPhoto);
                    WokrDataSetActivity.this.imgPath = uri.toString(); //절대경로 레트로핏 작업할때 전송해야함
                    Log.i("TAG", imgPath);
                }

            }
        }
    });


    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

}//WorkDataSetActivity class....