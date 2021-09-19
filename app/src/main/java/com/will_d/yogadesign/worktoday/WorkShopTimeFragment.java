package com.will_d.yogadesign.worktoday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.time.TimeAlarmFragment;
import com.will_d.yogadesign.time.TimeTimerFragment;

public class WorkShopTimeFragment extends Fragment {

    private TextView tvTimer;
    private TextView tvAlarm;
    private View viewLineTimer;
    private View viewLineAlarm;


    private Fragment[] fragments = new Fragment[2];
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTimer= view.findViewById(R.id.tv_timer);
        tvAlarm=view.findViewById(R.id.tv_alarm);
        viewLineTimer = view.findViewById(R.id.view_line_timer);
        viewLineAlarm = view.findViewById(R.id.view_line_alarm);

        fragments[0] = new TimeTimerFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        tran = fragmentManager.beginTransaction();
        tran.add(R.id.container2, fragments[0]);
        tran.show(fragments[0]);
        tran.commit();

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTimer.setTextColor(0xFF999999);
                tvAlarm.setTextColor(0xFF999999);
                viewLineTimer.setVisibility(View.INVISIBLE);
                viewLineAlarm.setVisibility(View.INVISIBLE);

                tvTimer.setTextColor(0xFF333333);
                viewLineTimer.setVisibility(View.VISIBLE);

                tran = fragmentManager.beginTransaction();
                tran.hide(fragments[0]);
                if (fragments[1]!=null) tran.hide(fragments[1]);

                tran.setCustomAnimations(R.anim.fragment_actionbar_translate_timer_end, R.anim.fragment_actionbar_translate_timer);
                tran.replace(R.id.container2, fragments[0]);
                tran.show(fragments[0]);
                tran.commit();
            }
        });

        tvAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTimer.setTextColor(0xFF999999);
                tvAlarm.setTextColor(0xFF999999);
                viewLineTimer.setVisibility(View.INVISIBLE);
                viewLineAlarm.setVisibility(View.INVISIBLE);

                tvAlarm.setTextColor(0xFF333333);
                viewLineAlarm.setVisibility(View.VISIBLE);

                tran = fragmentManager.beginTransaction();
                tran.hide(fragments[0]);
                if (fragments[1]!=null) tran.hide(fragments[1]);
                if (fragments[1]==null){
                    fragments[1] = new TimeAlarmFragment();
                    tran.add(R.id.container2, fragments[1]);
                }

                tran.setCustomAnimations(R.anim.fragment_actionbar_translate_alarm_end, R.anim.fragment_actionbar_translate_alarm);
                tran.replace(R.id.container2, fragments[1]);
                tran.show(fragments[1]);
                tran.commit();
            }
        });



    }
}
