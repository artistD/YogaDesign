package com.will_d.yogadesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class WorkTodayFragment extends Fragment {

    private ArrayList<WorkItem> workItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private WorkRecyclerAdapter adapter;
    ItemTouchHelper helper;

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
        return inflater.inflate(R.layout.fragment_work_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjCjgVv-4Cl9Z-XQT3uCV_KKtjPzSNG-q2XA&usqp=CAU";
        String imgUrl2 = "https://editorial.uefa.com/resources/026c-12f705c46a6c-9f2eb0579483-1000/messi_graphic.jpg";
        boolean[] weeks = new boolean[]{true, true, true, true, true, false, false};
        workItems.add(new WorkItem(imgUrl2, "도전", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl, "2", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl2, "3", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl, "4", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl2, "5", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl, "6", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));
        workItems.add(new WorkItem(imgUrl2, "7", true, true, false, "dqwdqwdqwdwqqw", "dqwdqwdqwdq", weeks));




        recyclerView =view.findViewById(R.id.recycler);
        adapter = new WorkRecyclerAdapter(getActivity(), workItems);
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));

        helper.attachToRecyclerView(recyclerView);



        cdAddBtn = view.findViewById(R.id.cd_addbtn);
        cdAddBtn2 = view.findViewById(R.id.cd_addbtn2);

        cdAddBtnItem = view.findViewById(R.id.cd_addbtn_item);
        cdAddBtnItem2 = view.findViewById(R.id.cd_addbtn_item2);

        cdAddBtnSub = view.findViewById(R.id.cd_addbtn_sub);
        cdAddBtnSub2 = view.findViewById(R.id.cd_addbtn_sub2);



        ani = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_fade);
        ani2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_fade_end);
        ani3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_layout_fade);
        ani4 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_layout_fade_end);

        rlBlur = view.findViewById(R.id.rl_Blur);
        workShopActivity = (WorkShopActivity)getActivity();



        cdAddBtn.setOnClickListener(new View.OnClickListener() {
            WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();
            @Override
            public void onClick(View v) {
                isclick=!isclick;
                if (isclick) {
                    cdAddBtnBeginning();
                    workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.VISIBLE);
                    workShopActivity.getMeterialCdTodolist().setCardElevation(0);
                    cdAddBtnItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), WokrDataSetActivity.class);
                            Gson gson = new Gson();
                            String jsonstr = gson.toJson(workItems);
                            Log.i("TAG", jsonstr);
                            intent.putExtra("Workitems", jsonstr);

                            activityResultLauncher.launch(intent);
                            getActivity().overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);
                            cdAddBtnEnd();
                            workShopActivity.getMeterialCdTodolist().setCardElevation(4);
                            workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.INVISIBLE);
                            isclick=false;
                        }
                    });

                    cdAddBtnSub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


                }
                else {
                    cdAddBtnEnd();
                    workShopActivity.getMeterialCdTodolist().setCardElevation(4);
                    workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.INVISIBLE);
                }

            }
        });

        setcdAddBtnToPreventBlurring();

    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == getActivity().RESULT_OK){
                Intent intent = result.getData();
            }
        }
    });

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
        workShopActivity.getToolbarBlur().startAnimation(ani3);

        workShopActivity.getToolbarBlur().setVisibility(View.VISIBLE);
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
        workShopActivity.getToolbarBlur().startAnimation(ani4);

        workShopActivity.getToolbarBlur().setVisibility(View.INVISIBLE);
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
