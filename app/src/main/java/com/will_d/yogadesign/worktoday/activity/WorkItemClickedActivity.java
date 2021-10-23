package com.will_d.yogadesign.worktoday.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.worktoday.adapter.LogItemAdapter;
import com.will_d.yogadesign.worktoday.item.LogItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkItemClickedActivity extends AppCompatActivity {

    private ImageView ivItemClickedToolbarBack;


    private TextView tvItemClickedNickname;
    private TextView tvItemClickedname;


    private ArrayList<Calendar> calendars = new ArrayList<>();
    private CalendarView calendarView;



    private LinearLayout llWorkitemClickedTimeSum;
    private TextView tvWorkitemClickedTimesum;
    private int hourSum =0;
    private int minuteSum=0;
    private boolean isTotal =false;
    private ArrayList<LogItem> logItems = new ArrayList<LogItem>();
    private LogItemAdapter adapter;
    private RecyclerView recyclerView;



    //프로그랬스바 영역임
    private ProgressBar progressBar;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_item_clicked);

        ivItemClickedToolbarBack = findViewById(R.id.iv_itemclicked_toolbar_back);
        tvItemClickedNickname = findViewById(R.id.tv_itemclicked_nickname);
        tvItemClickedname = findViewById(R.id.tv_itemclicked_name);
        calendarView = findViewById(R.id.calendar);
        recyclerView = findViewById(R.id.recycler);



        ivItemClickedToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        llWorkitemClickedTimeSum = findViewById(R.id.ll_workitem_clicked_timesum);
        tvWorkitemClickedTimesum = findViewById(R.id.tv_workitem_clicked_timesum);
        adapter = new LogItemAdapter(this, logItems);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progress);
        ll = findViewById(R.id.ll);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String nickName = intent.getStringExtra("nickName");
        tvItemClickedname.setText(name);
        tvItemClickedNickname.setText(nickName);
        String workItemNo = intent.getStringExtra("workItemNo");



        progressBar.setVisibility(View.VISIBLE);
        ll.setVisibility(View.INVISIBLE);

        logLoadDataFromServer(workItemNo);
        calendarDataLoadFromServer(workItemNo);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void calendarDataLoadFromServer(String workItemNo){
       Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
       RetrofitService retrofitService = retrofit.create(RetrofitService.class);

       Call<String> call = retrofitService.calendarDataLoadFromServer(workItemNo);
       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               String jsonStr = response.body();
               calendars.clear();

               try {
                   JSONArray jsonArray  = new JSONArray(jsonStr);
                   for (int i=0; i<jsonArray.length(); i++){
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       String days = jsonObject.getString("days");
                       Log.i("Tagcalendar", days);
                       String[] daysArr = days.split(":");
                       int year = Integer.parseInt(daysArr[0]);
                       int month = Integer.parseInt(daysArr[1]);
                       int day = Integer.parseInt(daysArr[2]);

                       Calendar calendar = Calendar.getInstance();
                       calendar.set(year, (month-1), day);
                       calendars.add(calendar);
                   }

                   calendarView.setSelectedDates(calendars);

               } catch (JSONException e) {
                   e.printStackTrace();
               }

               progressBar.setVisibility(View.INVISIBLE);
               ll.setVisibility(View.VISIBLE);

           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
                Log.i("Tagcalendar", t.getMessage());
           }
       });
    }

    public void logLoadDataFromServer(String workItemNo){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.logLoadDataFromServer(workItemNo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                logItems.clear();
                adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray  = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String no = jsonObject.getString("no");

                        String WorkItemNo = jsonObject.getString("WorkItemNo");
                        String days = jsonObject.getString("days");
                        String log = jsonObject.getString("log");
                        String time = jsonObject.getString("time");

                        if (!(time.equals("null"))) {
                            isTotal = true;
                            String[] timeArr = time.split(":");
                            hourSum = hourSum + (Integer.parseInt(timeArr[0])*60); //3시간 --> 180
                            minuteSum = minuteSum + Integer.parseInt(timeArr[1]);
                        }


                        Log.i("Tag", time);

                        logItems.add(0, new LogItem(days, log, time));
                        adapter.notifyItemChanged(0);

                    }

                    if (isTotal){
                        int total = (hourSum + minuteSum);
                        int finalHour = total/60;
                        int finalMinut = total - (finalHour*60);
                        String s = String.format("%02d:%02d", finalHour, finalMinut);
                        llWorkitemClickedTimeSum.setVisibility(View.VISIBLE);
                        tvWorkitemClickedTimesum.setText(s);
                    }else {
                        llWorkitemClickedTimeSum.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                progressBar.setVisibility(View.INVISIBLE);
                ll.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("retrofit", t.getMessage());
            }
        });
    }
}