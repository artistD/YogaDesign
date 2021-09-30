package com.will_d.yogadesign.time;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.will_d.yogadesign.R;

import soup.neumorphism.NeumorphCardView;

public class TimeTimerFragment extends Fragment {
    private TextView tvHour;
    private TextView tvMinutes;
    private TextView tvSeconds;


    private TextView tvInit;
    private NeumorphCardView ncdPlayAndPuase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHour = view.findViewById(R.id.tv_hour);
        tvMinutes = view.findViewById(R.id.tv_minutes);
        tvSeconds = view.findViewById(R.id.tv_seconds);

        tvInit = view.findViewById(R.id.tv_init);
        ncdPlayAndPuase = view.findViewById(R.id.ncd_playAndPuase);





    }
}
