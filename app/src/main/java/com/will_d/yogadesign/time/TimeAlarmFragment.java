package com.will_d.yogadesign.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class TimeAlarmFragment extends Fragment {



    private NeumorphCardView cdAddBtn;
    private NeumorphCardView cdAddBtn2;


    private ArrayList<AlarmItem> alarmItems = new ArrayList<AlarmItem>();
    private RecyclerView recyclerView;
    private AlarmItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cdAddBtn = view.findViewById(R.id.cd_addbtn);
        cdAddBtn2 = view.findViewById(R.id.cd_addbtn2);

        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

        boolean[] weeks = new boolean[]{true, true, true, true, false, false, false};
        alarmItems.add(new AlarmItem("08:30", weeks));
        alarmItems.add(new AlarmItem("09:30", weeks));
        alarmItems.add(new AlarmItem("10:30", weeks));
        alarmItems.add(new AlarmItem("11:00", weeks));
        alarmItems.add(new AlarmItem("12:30", weeks));


        recyclerView = view.findViewById(R.id.recycler);
        adapter = new AlarmItemAdapter(getActivity(), alarmItems);
        recyclerView.setAdapter(adapter);


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
