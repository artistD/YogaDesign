package com.will_d.yogadesign.worktoday.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.applandeo.materialcalendarview.CalendarView;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.worktoday.adapter.LogItemAdapter;
import com.will_d.yogadesign.worktoday.item.LogItem;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkItemClickedActivity extends AppCompatActivity {

    private ImageView ivItemClickedToolbarBack;
    private TextView tvItemClickedNickname;
    private TextView tvItemClickedname;
    private CalendarView calendarView;
    private RecyclerView recyclerView;

    private ArrayList<LogItem> logItems = new ArrayList<LogItem>();
    private LogItemAdapter adapter;


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

        //calendar 원하는 기능 완성
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 8, 27);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2021, 8, 28);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2021, 8, 29);

        ArrayList<Calendar> calendars = new ArrayList<>();
        calendars.add(calendar);
        calendars.add(calendar2);
        calendars.add(calendar3);
        calendarView.setSelectedDates(calendars);

        logItems.add(new LogItem("2021.9.23.(월)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.24.(화)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.25.(수)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.26.(목)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.27.(금)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.28.(토)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));
        logItems.add(new LogItem("2021.9.29.(일)", "adadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwqadadaqwdqwfqwfqwgqwgqwgwq"));


        adapter = new LogItemAdapter(this, logItems);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}