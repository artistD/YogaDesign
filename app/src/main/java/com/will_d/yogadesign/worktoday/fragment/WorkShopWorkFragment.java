package com.will_d.yogadesign.worktoday.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.month.WorkMonthFragment;
import com.will_d.yogadesign.worktoday.fragment.WorkTodayFragment;

public class WorkShopWorkFragment extends Fragment {

    private TextView tvToday;
    private TextView tvMonth;
    private View viewLineToday;
    private View viewLineMonth;


    private Fragment[] fragments = new Fragment[2];
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;

    private RelativeLayout toolbarBlur;


//##########
    public RelativeLayout getToolbarBlur() {
        return toolbarBlur;
    }

    public Fragment[] getFragments() { return fragments; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_work, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvToday= view.findViewById(R.id.tv_today);
        tvMonth=view.findViewById(R.id.tv_month);
        viewLineToday = view.findViewById(R.id.view_line_today);
        viewLineMonth = view.findViewById(R.id.view_line_month);

        toolbarBlur = view.findViewById(R.id.toolbar_blur);
        Log.i("TAG", "bbb");

        fragments[0] = new WorkTodayFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        tran = fragmentManager.beginTransaction();
        tran.add(R.id.container3, fragments[0]);
        tran.show(fragments[0]);
        tran.commit();



        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvToday.setTextColor(0xFF999999);
                tvMonth.setTextColor(0xFF999999);
                viewLineToday.setVisibility(View.INVISIBLE);
                viewLineMonth.setVisibility(View.INVISIBLE);

                tvToday.setTextColor(0xFF333333);
                viewLineToday.setVisibility(View.VISIBLE);

                tran = fragmentManager.beginTransaction();
                tran.hide(fragments[0]);
                if (fragments[1]!=null) tran.hide(fragments[1]);

                tran.setCustomAnimations(R.anim.fragment_actionbar_translate_timer_end, R.anim.fragment_actionbar_translate_timer);
                tran.replace(R.id.container3, fragments[0]);
                tran.show(fragments[0]);
                tran.commit();

            }
        });

        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tvToday.setTextColor(0xFF999999);
                tvMonth.setTextColor(0xFF999999);
                viewLineToday.setVisibility(View.INVISIBLE);
                viewLineMonth.setVisibility(View.INVISIBLE);

                tvToday.setTextColor(0xFF333333);
                viewLineMonth.setVisibility(View.VISIBLE);

                tran = fragmentManager.beginTransaction();
                tran.hide(fragments[0]);
                if (fragments[1]!=null) tran.hide(fragments[1]);
                if (fragments[1]==null){
                    fragments[1] = new WorkMonthFragment();
                    tran.add(R.id.container3, fragments[1]);
                }
                tran.setCustomAnimations(R.anim.fragment_actionbar_translate_alarm_end, R.anim.fragment_actionbar_translate_alarm);
                tran.replace(R.id.container3, fragments[1]);
                tran.show(fragments[1]);
                tran.commit();
            }
        });

    }

}