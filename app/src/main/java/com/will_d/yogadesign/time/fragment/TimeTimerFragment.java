package com.will_d.yogadesign.time.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.time.adapter.TimerAdapter;
import com.will_d.yogadesign.time.item.TimerItem;
import com.will_d.yogadesign.worktoday.item.TodolistItem;
import com.will_d.yogadesign.worktoday.item.WorkItem;

import java.util.ArrayList;
import java.util.Calendar;

import soup.neumorphism.NeumorphCardView;

public class TimeTimerFragment extends Fragment {
    private TextView tvTime;


    private TextView tvInit;
    private NeumorphCardView ncdPlayAndPuase;
    public ImageView ivPlayAndPuase;
    private TextView tvSave;


    private ArrayList<TimerItem> items = new ArrayList<>();
    private RelativeLayout rlClickSaveDialog;
    private RelativeLayout rldialogClickClose;
    private RecyclerView recyclerView;
    private TimerAdapter adapter;

    public TimeThread timeThread;
    public boolean isPlayAndPuase = false;


   public SharedPreferences pref;
   public SharedPreferences.Editor editor;

    //
    public ProgressBar progressBar;
    public RelativeLayout rl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTime = view.findViewById(R.id.tv_time);


        tvInit = view.findViewById(R.id.tv_init);
        ncdPlayAndPuase = view.findViewById(R.id.ncd_playAndPuase);
        ivPlayAndPuase = view.findViewById(R.id.iv_playAndPuase);
        tvSave = view.findViewById(R.id.tv_save);

        rlClickSaveDialog = view.findViewById(R.id.rl_clicksave_dialog);
        rldialogClickClose = view.findViewById(R.id.rl_dialog_clickclose);
        recyclerView = view.findViewById(R.id.recycler);

        progressBar = view.findViewById(R.id.progress);
        rl = view.findViewById(R.id.rl);

        pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        editor = pref.edit();


        if (!isPlayAndPuase){
            int hour = pref.getInt("hour", 0);
            int min  = pref.getInt("min", 0);
            int sec = pref.getInt("sec", 0);
            String time = String.format("%02d:%02d:%02d", hour, min, sec);
            tvTime.setText(time);
        }


        ncdPlayAndPuase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //true면 play false면 pause
                isPlayAndPuase = !isPlayAndPuase;
                if (isPlayAndPuase){
                    ivPlayAndPuase.setImageResource(R.drawable.ic_pause);
                    if (timeThread==null){
                        timeThread = new TimeThread();

                        int hour = pref.getInt("hour", 0);
                        int min  = pref.getInt("min", 0);
                        int sec = pref.getInt("sec", 0);
                        timeThread.time = hour;
                        timeThread.min = min;
                        timeThread.sec = sec;

                        adapter.setTimeThread(timeThread);
                        adapter.notifyDataSetChanged(); //이거해줘야하나????? //todo : 질문해서 풀어내자

                        timeThread.start();
                    }else {
                        timeThread.resumeThread();
                    }

                }else {
                    ivPlayAndPuase.setImageResource(R.drawable.ic_play);
                    if (timeThread !=null) {
                        timeThread.pauseThread();
                        editor.putInt("hour", timeThread.time);
                        editor.putInt("min", timeThread.min);
                        editor.putInt("sec", timeThread.sec);
                        editor.commit();
                    }

                }

            }
        });

        tvInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeThread!=null){
                    isPlayAndPuase = false;
                    ivPlayAndPuase.setImageResource(R.drawable.ic_play);
                    timeThread.stopThread();
                    timeThread.time=0;
                    timeThread.min=0;
                    timeThread.sec=0;
                    tvTime.setText("00:00:00");

                    editor.putInt("hour", timeThread.time);
                    editor.putInt("min", timeThread.min);
                    editor.putInt("sec", timeThread.sec);
                    editor.commit();
                    timeThread = null;
                    adapter.setTimeThread(null);
                    adapter.notifyDataSetChanged();

                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayAndPuase = false;
                ivPlayAndPuase.setImageResource(R.drawable.ic_play);
                if (timeThread !=null) {
                    timeThread.pauseThread();

                    editor.putInt("hour", timeThread.time);
                    editor.putInt("min", timeThread.min);
                    editor.putInt("sec", timeThread.sec);
                    editor.commit();
                }
                rlClickSaveDialog.setVisibility(View.VISIBLE);

            }
        });

        rldialogClickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlClickSaveDialog.setVisibility(View.INVISIBLE);
            }
        });


//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));
//        items.add(new TimerItem("asdad"));




        adapter = new TimerAdapter(getActivity(), items, rlClickSaveDialog, tvTime);
        recyclerView.setAdapter(adapter);


        items.clear();
        adapter.notifyDataSetChanged();
        for (int i=0; i<Global.workItems.size(); i++){
            WorkItem workItem = Global.workItems.get(i);
            boolean[] weeksData = workItem.getIsweeks();

            Calendar cal = Calendar.getInstance();
            int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
            switch (day_of_week){
                case 1://일
                    if (weeksData[6]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);

                    }
                    break;

                case 2://월
                    if (weeksData[0]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;

                case 3://화
                    if (weeksData[1]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;

                case 4://수
                    if (weeksData[2]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;

                case 5://목
                    if (weeksData[3]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;

                case 6://금
                    if (weeksData[4]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;

                case 7://토
                    if (weeksData[5]){
                        items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                        adapter.notifyItemInserted(0);
                    }
                    break;
            }
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
            if (!isPlayAndPuase){
                int hour = pref.getInt("hour", 0);
                int min  = pref.getInt("min", 0);
                int sec = pref.getInt("sec", 0);
                String time = String.format("%02d:%02d:%02d", hour, min, sec);
                tvTime.setText(time);
            }



            items.clear();
            adapter.notifyDataSetChanged();
            for (int i=0; i<Global.workItems.size(); i++){
                WorkItem workItem = Global.workItems.get(i);
                boolean[] weeksData = workItem.getIsweeks();

                Calendar cal = Calendar.getInstance();
                int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
                switch (day_of_week){
                    case 1://일
                        if (weeksData[6]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);

                        }
                        break;

                    case 2://월
                        if (weeksData[0]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;

                    case 3://화
                        if (weeksData[1]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;

                    case 4://수
                        if (weeksData[2]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;

                    case 5://목
                        if (weeksData[3]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;

                    case 6://금
                        if (weeksData[4]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;

                    case 7://토
                        if (weeksData[5]){
                            items.add(0, new TimerItem(workItem.getNo(), workItem.getNickName()));
                            adapter.notifyItemInserted(0);
                        }
                        break;
                }
            }
        }


    }


    public class TimeThread extends Thread {
        public boolean isRun = true;
        public boolean isWait = false;

        public int time, min, sec;

        @Override
        public void run() {

            while (isRun) {
                sec++;
                if (sec >= 60) {
                    sec = 0;
                    min++;
                    if (min >= 60) {
                        min = 0;
                        time++;
                        if (time>=24){
                            time=0;
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = String.format("%02d:%02d:%02d", time, min, sec);
                        tvTime.setText(s);
                    }
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (this){
                    if (isWait) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }//run method...

        public void stopThread(){
            isRun = false;

            synchronized (this){
                this.notify();
            }
        }

        public void pauseThread(){
            isWait =true;
        }

        public void resumeThread(){
            isWait =false;

            synchronized (this){
                this.notify();
            }
        }

    }//TimeThread inner class.....
}
