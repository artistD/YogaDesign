package com.will_d.yogadesign.time;

import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class TimeTimerFragment extends Fragment {
    private TextView tvTime;


    private TextView tvInit;
    private NeumorphCardView ncdPlayAndPuase;
    private ImageView ivPlayAndPuase;
    private TextView tvSave;

    private RelativeLayout rlClickSaveDialog;
    private RecyclerView recyclerView;

    private TimeThread timeThread;
    private boolean isPlayAndPuase = false;

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
        recyclerView = view.findViewById(R.id.recycler);


        ncdPlayAndPuase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //true면 play false면 pause
                isPlayAndPuase = !isPlayAndPuase;
                if (isPlayAndPuase){
                    ivPlayAndPuase.setImageResource(R.drawable.ic_pause);
                    if (timeThread==null){
                        timeThread = new TimeThread();
                        timeThread.start();
                    }else {
                        timeThread.resumeThread();
                    }

                }else {
                    ivPlayAndPuase.setImageResource(R.drawable.ic_play);
                    if (timeThread !=null) timeThread.pauseThread();
                }

            }
        });

        tvInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeThread!=null){
                    ivPlayAndPuase.setImageResource(R.drawable.ic_play);
                    timeThread.stopThread();
                    timeThread.time=0;
                    timeThread.min=0;
                    timeThread.sec=0;
                    tvTime.setText("00:00:00");
                    timeThread = null;
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    class TimeThread extends Thread {
        boolean isRun = true;
        boolean isWait = false;

        int time, min, sec;

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
                    Thread.sleep(1000);
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

        void stopThread(){
            isRun = false;

            synchronized (this){
                this.notify();
            }
        }

        void pauseThread(){
            isWait =true;
        }

        void resumeThread(){
            isWait =false;

            synchronized (this){
                this.notify();
            }
        }

    }//TimeThread inner class.....
}
