package com.will_d.yogadesign.worktoday.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.worktoday.GworkToday;
import com.will_d.yogadesign.worktoday.RetrofitHelper;
import com.will_d.yogadesign.worktoday.RetrofitService;
import com.will_d.yogadesign.worktoday.fragment.WorkShopTodolistFragment;
import com.will_d.yogadesign.worktoday.fragment.WorkTodayFragment;
import com.will_d.yogadesign.worktoday.item.WorkItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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

    private CardView cdWeeksDialogSelectedDays;
    private CardView cdWeeksDialogSelectedToday;
    private CardView cdWeeksDialogDaysBlur;

    private TextView tvWeeksDialogSelectedDays;
    private TextView tvWeeksDialogSelectedToday;

    private boolean isSelectedDays = false;
    private boolean isSelectedToday = false;


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
    private String id;
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
    private double latitude=37.560955;
    private double longitude=127.034721;
    private boolean isItemOnOff = true;
    private boolean isItemPublic =true;
    private int completeNum = 0;
    private boolean[] todolistBooleanState = new boolean[] {false,false,false};
    private boolean[] isDayOrTodaySelected = new boolean[2];

    private boolean isLogModify = false;
    //##############################

    private double originalLatitude;
    private double originalLongitude;

    private boolean isPhotoChecekd=false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wokr_data_set);
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        id = pref.getString("id", "");


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

        //todo:StackOverFlowError, OutOfMemory 에러
        //Gson 작업
//        Intent intent =getIntent();
//        String jsonStr = intent.getStringExtra("Workitems");
//        Gson gson = new Gson();
//        WorkItem[] workItem = gson.fromJson(jsonStr, WorkItem[].class);
        workItems = GworkToday.workItems;

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

        cdWeeksDialogSelectedDays = findViewById(R.id.cd_weeks_dialog_selected_days);
        cdWeeksDialogSelectedToday = findViewById(R.id.cd_weeks_dialog_selected_today);
        cdWeeksDialogDaysBlur = findViewById(R.id.cd_weeks_dialog_days_blur);

        tvWeeksDialogSelectedDays = findViewById(R.id.tv_weeks_dialog_selected_days);
        tvWeeksDialogSelectedToday = findViewById(R.id.tv_weeks_dialog_selected_today);

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
                            googleMap.clear();
                            LatLng userLocation = new LatLng(37.560955, 127.034721);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18));
                            MarkerOptions marker = new MarkerOptions();
                            marker.position(userLocation);
                            marker.title("나의 위치");
                            marker.snippet("알림받고 싶은 위치를 지정해주세요!");
                            marker.isVisible();

                            googleMap.addMarker(marker);


                            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    googleMap.clear();
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                    MarkerOptions marker = new MarkerOptions();
                                    marker.position(latLng);
                                    marker.title("나의 위치");
                                    marker.snippet("이곳으로 알림을 보내드릴게요!");
                                    marker.isVisible();

                                    googleMap.addMarker(marker);

                                    latitude = latLng.latitude;
                                    longitude = latLng.longitude;

                                    Log.i("TAG",   latitude + " : " + longitude + "");
                                }
                            });

                            nivLocalNotificationSearchComplete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String addr = etLocalNotificationSearch.getText().toString();
                                    Geocoder geocoder = new Geocoder(WokrDataSetActivity.this, Locale.KOREA);
                                    try {
                                        googleMap.clear();
                                        List<Address> address  = geocoder.getFromLocationName(addr, 3);

                                        double lat = address.get(0).getLatitude();
                                        double lon = address.get(0).getLongitude();

                                        LatLng userLocation = new LatLng(lat, lon);
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18));

                                        MarkerOptions marker = new MarkerOptions();
                                        marker.position(userLocation);
                                        marker.title("검색한 나의 위치");
                                        marker.snippet("알림을 받고싶은 위치를 지정해주세요!");
                                        marker.isVisible();

                                        googleMap.addMarker(marker);

                                        latitude = lat;
                                        longitude = lon;

                                    } catch (IOException e) {
                                        Toast.makeText(WokrDataSetActivity.this, "검색 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        }
                    });

                    tvLocalNotificationOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            originalLatitude  =latitude;
                            originalLongitude = longitude;

                            placeName = etLocalNotificationPlaceName.getText().toString();
                            tvLocalNotifiacation.setText(placeName);
                            tvLocalNotifiacation.setTextColor(0xFF9999FF);
                            localNotificationDialog.setVisibility(View.INVISIBLE);
                        }
                    });

                    tvLocalNotificationCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            latitude = 37.560955;
                            longitude = 127.034721;
                            isLocalNotificationChecked = false;
                            swLocalNotification.setChecked(false);
                            tvLocalNotifiacation.setText("위치알림");
                            tvLocalNotifiacation.setTextColor(0xFF666666);
                            mcdLocalNotificationFixed.setVisibility(View.INVISIBLE);
                            localNotificationDialog.setVisibility(View.INVISIBLE);
                        }
                    });


                }else {
                    latitude = 37.560955;
                    longitude = 127.034721;
                    isLocalNotificationChecked = isChecked;
                    mcdLocalNotificationFixed.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        //수정을 위해 startActivity가 오면 어떻게 동작할건지 설정
        if (GworkToday.isworkitemModifyChcecked){
            WorkItemModifyDataLoadDB(GworkToday.no);
        }

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
        GworkToday.isModifySave = false;
        finish();
        overridePendingTransition(R.anim.fragment_none, R.anim.activity_data_set_end);
    }

    public void clickClose(View view) {
        GworkToday.isModifySave = false;
        finish();
        overridePendingTransition(R.anim.fragment_none, R.anim.activity_data_set_end);
    }

    public void clickSave(View view) { //********************************

        if (etName.getText().toString().equals("") || tvNickname.equals("별명") || imgPath.equals("") || (!weeksData[0]&&!weeksData[1]&&!weeksData[2]&&!weeksData[3]&&!weeksData[4]&&!weeksData[5]&&!weeksData[6])){
            Toast.makeText(this, "이름, 별명, 사진, 반복 선택은 필수 입니다.", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = getIntent();
        if (GworkToday.isModifySave){
            name = etName.getText().toString();
            String no = intent.getStringExtra("no");
            Log.i("qazw", no);
            workTodayModifyUpdateToServer(isPhotoChecekd, no);
            GworkToday.isModifySave=false;

        }else {
            name = etName.getText().toString();
            WorkTodayDataToServer();
        }

        WorkTodayFragment.isWorkItemAdd = true;
        WorkShopTodolistFragment.isWorkItemAdd = true;


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


        cdWeeksDialogSelectedDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectedDays = false;
                isSelectedToday = false;

                isSelectedDays = true;

                tvWeeksDialogSelectedDays.setTextColor(0xff666666);
                tvWeeksDialogSelectedToday.setTextColor(0xff666666);

                tvWeeksDialogSelectedDays.setTextColor(0xff9999FF);
               cdWeeksDialogDaysBlur.setVisibility(View.INVISIBLE);
                for (int i=0; i<weeksData.length; i++){
                    weeksData[i] =false;
                }

                if (!weeksData[0]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[0]);
                if (!weeksData[1]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[1]);
                if (!weeksData[2]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[2]);
                if (!weeksData[3]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[3]);
                if (!weeksData[4]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[4]);
                if (!weeksData[5]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[5]);
                if (!weeksData[6]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[6]);
            }
        });

        cdWeeksDialogSelectedToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectedDays = false;
                isSelectedToday = false;

                isSelectedToday = true;


                tvWeeksDialogSelectedDays.setTextColor(0xff666666);
                tvWeeksDialogSelectedToday.setTextColor(0xff666666);

                tvWeeksDialogSelectedToday.setTextColor(0xff9999FF);
                cdWeeksDialogDaysBlur.setVisibility(View.VISIBLE);
                for (int i=0; i<weeksData.length; i++){
                    weeksData[i] =false;
                }

                if (!weeksData[0]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[0]);
                if (!weeksData[1]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[1]);
                if (!weeksData[2]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[2]);
                if (!weeksData[3]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[3]);
                if (!weeksData[4]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[4]);
                if (!weeksData[5]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[5]);
                if (!weeksData[6]) Glide.with(WokrDataSetActivity.this).load(R.drawable.ic_unchecked).into(weeks[6]);


            }
        });

        tvWeeksDialogSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isSelectedDays && !isSelectedToday){
                    Toast.makeText(WokrDataSetActivity.this, "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (isSelectedDays && !weeksData[0] && !weeksData[1] && !weeksData[2] && !weeksData[3] && !weeksData[4] && !weeksData[5] && !weeksData[6]){
                    Toast.makeText(WokrDataSetActivity.this, "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!isSelectedDays && isSelectedToday){

                    isDayOrTodaySelected[0] = isSelectedDays;
                    isDayOrTodaySelected[1] = isSelectedToday;

                    Log.i("TAG", "" + isDayOrTodaySelected[0] + isDayOrTodaySelected[1]);

                    Calendar calendar = Calendar.getInstance();
                    int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

                    switch (day_of_week){
                        case 1: //일
                            weeksData[6] = true;
                            break;

                        case 2: //월
                            weeksData[0] = true;
                            break;

                        case 3: //화
                            weeksData[1] = true;
                            break;

                        case 4: //수
                            weeksData[2] = true;
                            break;

                        case 5: //목
                            weeksData[3] = true;
                            break;

                        case 6: //금
                            weeksData[4] = true;
                            break;

                        case 7: //토
                            weeksData[5] = true;
                            break;
                    }

                    weeksDialog.setVisibility(View.INVISIBLE);
                    tvWeeks.setText("오늘 하루만");
                    tvWeeks.setTextColor(0xFF9999FF);
                    Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);

                }else {

                    isDayOrTodaySelected[0] = isSelectedDays;
                    isDayOrTodaySelected[1] = isSelectedToday;

                    Log.i("TAG", "" + isDayOrTodaySelected[0] + isDayOrTodaySelected[1]);

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
                    }
                    Log.i("TAG", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);
                }



            }
        });

    }

    public void clickNickNamePick(View view) {
        TextView tv = (TextView) view;
        if (workItems !=null){
            for (int i=0; i<workItems.size(); i++){
                if (workItems.get(i).getNickName().equals(tv.getText().toString())){
                    Toast.makeText(this, "별명이 중복될수는 없습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if (workItems != null ){
                    if (etNickNameDialogDirectlyInput.getText().toString().length()>0){
                        for (int i=0; i<workItems.size(); i++){
                            if (workItems.get(i).getNickName().equals(etNickNameDialogDirectlyInput.getText().toString())){
                                Toast.makeText(WokrDataSetActivity.this, "별명이 중복될수는 없습니다!", Toast.LENGTH_SHORT).show();
                                return;
                            }
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

        etLocalNotificationPlaceName.setText(placeName);
        mcdLocalNotificationFixed.setVisibility(View.VISIBLE);
        localNotificationDialog.setVisibility(View.VISIBLE);
        Log.i("TAG", isLocalNotificationChecked+"");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.clear();
                LatLng userLocation = new LatLng(latitude, longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18));
                MarkerOptions marker = new MarkerOptions();
                marker.position(userLocation);
                marker.title("나의 위치");
                marker.snippet("알림받고 싶은 위치를 지정해주세요!");
                marker.isVisible();

                googleMap.addMarker(marker);


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        googleMap.clear();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                        MarkerOptions marker = new MarkerOptions();
                        marker.position(latLng);
                        marker.title("나의 위치");
                        marker.snippet("이곳으로 알림을 보내드릴게요!");
                        marker.isVisible();

                        googleMap.addMarker(marker);

                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                    }
                });

                nivLocalNotificationSearchComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addr = etLocalNotificationSearch.getText().toString();
                        Geocoder geocoder = new Geocoder(WokrDataSetActivity.this, Locale.KOREA);
                        try {
                            googleMap.clear();
                            List<Address> address  = geocoder.getFromLocationName(addr, 3);

                            double lat = address.get(0).getLatitude();
                            double lon = address.get(0).getLongitude();

                            LatLng userLocation = new LatLng(lat, lon);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18));

                            MarkerOptions marker = new MarkerOptions();
                            marker.position(userLocation);
                            marker.title("검색한 나의 위치");
                            marker.snippet("알림을 받고싶은 위치를 지정해주세요!");
                            marker.isVisible();

                            googleMap.addMarker(marker);

                            latitude = lat;
                            longitude = lon;

                        } catch (IOException e) {
                            Toast.makeText(WokrDataSetActivity.this, "검색 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        tvLocalNotificationOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeName = etLocalNotificationPlaceName.getText().toString();
                tvLocalNotifiacation.setText(placeName);
                tvLocalNotifiacation.setTextColor(0xFF9999FF);
                localNotificationDialog.setVisibility(View.INVISIBLE);
            }
        });

        tvLocalNotificationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = originalLatitude;
                longitude = originalLongitude;
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
            dataPart.put("id", id);

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

            dataPart.put("placeName", placeName);
            dataPart.put("latitude", String.valueOf(latitude));
            dataPart.put("longitude", String.valueOf(longitude));
            dataPart.put("isItemOnOff", String.valueOf(isItemOnOff));
            dataPart.put("isItemPublic", String.valueOf(isItemPublic));

            String todolistBooleanJsonStr = gson.toJson(todolistBooleanState);
            Log.i("TAGBoolean", todolistBooleanJsonStr);
            dataPart.put("todoistBooleanState", todolistBooleanJsonStr);

            dataPart.put("completeNum", String.valueOf(completeNum));

            String jsonStr = gson.toJson(isDayOrTodaySelected);
            dataPart.put("isDayOrTodaySelected", jsonStr);
            Log.i("TAGadadad", jsonStr);

            dataPart.put("isLogModify", String.valueOf(isLogModify));

            Call<String> call = retrofitService.WorkItemPostDataToServer(dataPart, filePart);
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

    public void workTodayModifyUpdateToServer(boolean isPhotoChecekd, String no){
        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);


        //여기서 보내냐 안보내냐를 판단해야함..................................................
        MultipartBody.Part filePart = null;
        Log.i("bnm", isPhotoChecekd+"");
            if (isPhotoChecekd) {
                String realImgagePath = getRealPathFromUri(Uri.parse(imgPath)); ////////////*******************
                File file = new File(realImgagePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
            }

            Map<String, String> dataPart = new HashMap<>();
            dataPart.put("id", id);
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

            dataPart.put("placeName", placeName);
            dataPart.put("latitude", String.valueOf(latitude));
            dataPart.put("longitude", String.valueOf(longitude));
            dataPart.put("isItemOnOff", String.valueOf(isItemOnOff));
            dataPart.put("isItemPublic", String.valueOf(isItemPublic));

            String todolistBooleanJsonStr = gson.toJson(todolistBooleanState);
            Log.i("TAGBoolean", todolistBooleanJsonStr);
            dataPart.put("todoistBooleanState", todolistBooleanJsonStr);

            dataPart.put("completeNum", String.valueOf(completeNum));

            String jsonStr = gson.toJson(isDayOrTodaySelected);
            dataPart.put("isDayOrTodaySelected", jsonStr);
            Log.i("TAGadadad", jsonStr);

            dataPart.put("isPhotoChecekd", String.valueOf(isPhotoChecekd));
            dataPart.put("no", no);


            Call<String> call = retrofitService.workTodayModifyUpdateToServer(dataPart, filePart);
            Log.i("qazxc", filePart+"");
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

    public void WorkItemModifyDataLoadDB(String no){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.WorkItemModifyDataLoadDB(no);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                GworkToday.isworkitemModifyChcecked = false;
                String jsonStr = response.body();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    name = jsonObject.getString("name");
                    nickName = jsonObject.getString("nickName");

                    String dstName = jsonObject.getString("dstName");
                    imgPath = "http://willd88.dothome.co.kr/YogaDesign2/workitem/" + dstName;

                    String weeksDataJsonStr = jsonObject.getString("weeksData");
                    Gson gson = new Gson();
                    weeksData = gson.fromJson(weeksDataJsonStr, boolean[].class);

                    String isg = jsonObject.getString("isGoalChecked");
                    if (isg.equals("1")){
                        isGoalChecked = true;
                    }else if (isg.equals("0")){
                        isGoalChecked = false;
                    }

                    goalSet = jsonObject.getString("goalSet");

                    String isPre = jsonObject.getString("isPreNotificationChecked");

                    if (isPre.equals("1")){
                        isPreNotificationChecked = true;
                    }else if(isPre.equals("0")){
                        isPreNotificationChecked = false;
                    }
                    preNotificationTime = jsonObject.getString("preNotificationTime");


                    String isLocal = jsonObject.getString("isLocalNotificationChecked");
                    if (isLocal.equals("1")){
                        isLocalNotificationChecked = true;
                    }else if(isLocal.equals("0")){
                        isLocalNotificationChecked = false;
                    }

                    placeName = jsonObject.getString("placeName");

                    String lati = jsonObject.getString("latitude");
                    latitude = Double.parseDouble(lati);

                    String longi = jsonObject.getString("longitude");
                    longitude = Double.parseDouble(longi);


                    String isItem = jsonObject.getString("isItemOnOff");
                    if (isItem.equals("1")){
                        isItemOnOff = true;
                    }else if(isItem.equals("0")){
                        isItemOnOff = false;
                    }

                    String isItemP = jsonObject.getString("isItemPublic");
                    if (isItemP.equals("1")){
                        isItemPublic = true;
                    }else if(isItemP.equals("0")){
                        isItemPublic = false;
                    }

                    String cNum = jsonObject.getString("Completenum");
                    completeNum = Integer.parseInt(cNum);

                    String isDayOrToday = jsonObject.getString("isDayOrTodaySelected");
                    isDayOrTodaySelected = gson.fromJson(isDayOrToday, boolean[].class);


//                    Log.i("qazxc", name);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", imgPath);
//                    Log.i("qazxc", weeksData+"");
//                    Log.i("qazxc", isGoalChecked+"");
//                    Log.i("qazxc", goalSet+"");
//                    Log.i("qazxc", isPreNotificationChecked);
//                    Log.i("qazxc", preNotificationTime);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);
//                    Log.i("qazxc", nickName);



                        etName.setText(name);
                        tvNickname.setText(nickName);
                        tvNickname.setTextColor(0xFF9999FF);

                        tvPhoto.setVisibility(View.INVISIBLE);
                        civPhoto.setVisibility(View.VISIBLE);
                        Log.i("qazxc", imgPath);
                        Glide.with(WokrDataSetActivity.this).load(imgPath).into(civPhoto);



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
                        }

                    if (!isDayOrTodaySelected[0] && isDayOrTodaySelected[1]){
                        isSelectedDays = isDayOrTodaySelected[0];
                        isSelectedToday = isDayOrTodaySelected[1];

                        cdWeeksDialogDaysBlur.setVisibility(View.VISIBLE);
                        tvWeeksDialogSelectedToday.setTextColor(0xFF9999FF);
                        tvWeeks.setText("오늘 하루만");
                        tvWeeks.setTextColor(0xFF9999FF);
                    }else if (isDayOrTodaySelected[0] && !isDayOrTodaySelected[1]){
                        isSelectedDays = isDayOrTodaySelected[0];
                        isSelectedToday = isDayOrTodaySelected[1];

                        cdWeeksDialogDaysBlur.setVisibility(View.INVISIBLE);
                        tvWeeksDialogSelectedDays.setTextColor(0xFF9999FF);
                        tvWeeks.setText(replaceFirst);
                        tvWeeks.setTextColor(0xFF9999FF);

                        if (!weeksData[0]) weeks[0].setImageResource(R.drawable.ic_unchecked);
                        else weeks[0].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[1]) weeks[1].setImageResource(R.drawable.ic_unchecked);
                        else weeks[1].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[2]) weeks[2].setImageResource(R.drawable.ic_unchecked);
                        else weeks[2].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[3]) weeks[3].setImageResource(R.drawable.ic_unchecked);
                        else weeks[3].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[4]) weeks[4].setImageResource(R.drawable.ic_unchecked);
                        else weeks[4].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[5]) weeks[5].setImageResource(R.drawable.ic_unchecked);
                        else weeks[5].setImageResource(R.drawable.ic_checked);
                        if (!weeksData[6]) weeks[6].setImageResource(R.drawable.ic_unchecked);
                        else weeks[6].setImageResource(R.drawable.ic_checked);


                    }









                        if (isGoalChecked){
                            swGoal.setChecked(true);
                            goalDialog.setVisibility(View.INVISIBLE);
                            mcdGoalFixed.setVisibility(View.VISIBLE);
                            tvGoal.setText(goalSet);
                            tvGoal.setTextColor(0xFF9999FF);
                            if (goalSet!=null){
                                String a = String.valueOf(goalSet.charAt(4));
                                Log.i("TAGzxc", a);
                                int num = Integer.parseInt(a);
                                numberPickerGoalNumber.setValue(num);
                            }
                        }

                        if (isPreNotificationChecked){
                            swPreNotification.setChecked(true);
                            preNotificationDialog.setVisibility(View.INVISIBLE);
                            mcdPreNotificationFixed.setVisibility(View.VISIBLE);
                            tvPreNotification.setText(preNotificationTime);
                            tvPreNotification.setTextColor(0xFF9999FF);
                            String[] split = preNotificationTime.split(":");
                            int hour = Integer.parseInt(split[0]);
                            int min = Integer.parseInt(split[1]);
                            timePickerPreNotification.setHour(hour);
                            timePickerPreNotification.setMinute(min);
                        }
                        if (isLocalNotificationChecked){
                            swLocalNotification.setChecked(true);
                            localNotificationDialog.setVisibility(View.INVISIBLE);
                            mcdLocalNotificationFixed.setVisibility(View.VISIBLE);
                            tvLocalNotifiacation.setText(placeName);;
                            tvLocalNotifiacation.setTextColor(0xFF9999FF);
                        }









                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void workModifyDeleteDB(String no){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call= retrofitService.workitemDeleteDB(no);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                GworkToday.isModifySave =false;
                Log.i("TAG", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });
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
                    Log.i("TAGzxcv", imgPath);
                    isPhotoChecekd = true;
                }

            }else{
                isPhotoChecekd = false;
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