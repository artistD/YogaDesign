package com.will_d.yogadesign.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class TimeAlarmFragment extends Fragment {

    ArrayList<AlarmItem> items = new ArrayList<AlarmItem>();


    private NeumorphCardView cdAddBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cdAddBtn = view.findViewById(R.id.cd_addbtn);

        cdAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmDataSetActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);
            }
        });
    }
}
