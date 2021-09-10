package com.will_d.yogadesign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class WorkShopWorkFragment extends Fragment {

    private ArrayList<WorkItem> workItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private WorkRecyclerAdapter adapter;

    private NeumorphCardView cdAddBtn;
    private NeumorphCardView cdAddBtn2;

    private NeumorphCardView cdAddBtnItem;
    private NeumorphCardView cdAddBtnItem2;
    private NeumorphCardView cdAddBtnSub;
    private NeumorphCardView cdAddBtnSub2;

    private boolean isclick = false;
    private RelativeLayout rlBlur;

    private WorkShopActivity workShopActivity;

    private Animation ani;
    private Animation ani2;
    private Animation ani3;
    private Animation ani4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_work, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjCjgVv-4Cl9Z-XQT3uCV_KKtjPzSNG-q2XA&usqp=CAU";
        boolean[] weeks = new boolean[]{true, true, true, true, true, false, false};
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));
        workItems.add(new WorkItem(imgUrl, "passion", true, true, false, "dwqqwfgfwqqfqqfqwfqwf", "fqwfqwqwfqwffqw", weeks));


        recyclerView =view.findViewById(R.id.recycler);
        adapter = new WorkRecyclerAdapter(getActivity(), workItems);
        recyclerView.setAdapter(adapter);

        cdAddBtn = view.findViewById(R.id.cd_addbtn);
        cdAddBtn2 = view.findViewById(R.id.cd_addbtn2);

        cdAddBtnItem = view.findViewById(R.id.cd_addbtn_item);
        cdAddBtnItem2 = view.findViewById(R.id.cd_addbtn_item2);

        cdAddBtnSub = view.findViewById(R.id.cd_addbtn_sub);
        cdAddBtnSub2 = view.findViewById(R.id.cd_addbtn_sub2);



        ani = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_fade);
        ani2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_translate_end);
        ani3 = AnimationUtils.loadAnimation(getActivity(), R.anim.layout_fade);
        ani4 = AnimationUtils.loadAnimation(getActivity(), R.anim.layout_fade_end);

        rlBlur = view.findViewById(R.id.rl_Blur);
        workShopActivity = (WorkShopActivity)getActivity();



            cdAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isclick=!isclick;
                    if (isclick) cdAddBtnBeginning();
                    else cdAddBtnEnd();

                }
            });

            setcdAddBtnToPreventBlurring();

    }

    public void cdAddBtnBeginning(){
        cdAddBtnItem.startAnimation(ani);
        cdAddBtnItem2.startAnimation(ani);
        cdAddBtnSub.startAnimation(ani);
        cdAddBtnSub2.startAnimation(ani);

        cdAddBtnItem.setVisibility(View.VISIBLE);
        cdAddBtnItem2.setVisibility(View.VISIBLE);
        cdAddBtnSub.setVisibility(View.VISIBLE);
        cdAddBtnSub2.setVisibility(View.VISIBLE);


        workShopActivity.getViewLine().startAnimation(ani3);
        workShopActivity.getIvBnvBlur().startAnimation(ani3);
        rlBlur.startAnimation(ani3);

        workShopActivity.getViewLine().setVisibility(View.INVISIBLE);
        workShopActivity.getIvBnvBlur().setVisibility(View.VISIBLE);
        rlBlur.setVisibility(View.VISIBLE);
    }

    public void cdAddBtnEnd(){
        cdAddBtnItem.startAnimation(ani2);
        cdAddBtnSub.startAnimation(ani2);

        cdAddBtnItem.setVisibility(View.INVISIBLE);
        cdAddBtnItem2.setVisibility(View.INVISIBLE);
        cdAddBtnSub.setVisibility(View.INVISIBLE);
        cdAddBtnSub2.setVisibility(View.INVISIBLE);

        workShopActivity.getViewLine().startAnimation(ani4);
        workShopActivity.getIvBnvBlur().startAnimation(ani4);
        rlBlur.startAnimation(ani4);

        rlBlur.setVisibility(View.INVISIBLE);
        workShopActivity.getViewLine().setVisibility(View.VISIBLE);
        workShopActivity.getIvBnvBlur().setVisibility(View.INVISIBLE);

    }

    public void setcdAddBtnToPreventBlurring(){
        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

        cdAddBtnItem.setBackgroundColor(0xFFC7DDFF);
        cdAddBtnItem2.setBackgroundColor(0xFFC7DDFF);

        cdAddBtnSub.setBackgroundColor(0xFFC7DDFF);
        cdAddBtnSub2.setBackgroundColor(0xFFC7DDFF);

    }





}
