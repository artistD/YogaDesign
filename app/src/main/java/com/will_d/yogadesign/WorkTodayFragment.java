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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
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

//WorkItems data ****************************
    private String name="";
    private String nickName = "";
    private String imgPath = "";
    private boolean[] weeksData = new boolean[7];
    private boolean isGoalChecked = false;
    private String goalSet = "";
    private boolean isPreNotificationChecked = false;
    private String preNotificationTime = "";
    private boolean isLocalNotificationChecked = false;
    private String placeName = "서울";
    private double latitude = 0.0;
    private double longitude = 0.0;
//********************************************


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //리사이클러뷰 테스트
//        String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjCjgVv-4Cl9Z-XQT3uCV_KKtjPzSNG-q2XA&usqp=CAU";
//        boolean[] weeks = new boolean[]{true, true, true, true, true, false, false};
//          workItems.add(new WorkItem(imgUrl, "1", true, true, false, "dddddqwdq", "dqwfwqfwqf", weeks));
        

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

//                name = intent.getStringExtra("name");
//                nickName = intent.getStringExtra("nickName");
//                imgPath = intent.getStringExtra("imgPath");
//                weeksData = intent.getBooleanArrayExtra("weeksData");
//                isGoalChecked = intent.getBooleanExtra("isGoalChecked", false);
//                goalSet= intent.getStringExtra("goalSet");
//                isPreNotificationChecked = intent.getBooleanExtra("isPreNotificationChecked", false);
//                preNotificationTime = intent.getStringExtra("preNotificationTime");
//                isLocalNotificationChecked = intent.getBooleanExtra("isLocalNotificationChecked", false);
//                latitude = intent.getDoubleExtra("latitude", 37.560955);
//                longitude = intent.getDoubleExtra("longitude", 127.034721);
//                placeName = intent.getStringExtra("placeName");

//                workItems.add(0, new WorkItem(imgPath, nickName, name, isGoalChecked, goalSet, isPreNotificationChecked, preNotificationTime, isLocalNotificationChecked, placeName, weeksData));
//                adapter.notifyItemChanged(0);

                //** 최종데이터 전송
                Log.i("FinalChecked", "name : " + name);
                Log.i("FinalChecked", "nickName : " + nickName);
                Log.i("FinalChecked", "imgPath : " + imgPath);
                Log.i("FinalChecked", "월 : " + weeksData[0] + ", " + "화 : " + weeksData[1] + ", " + "수 : " + weeksData[2] + ", " + "목 : " + weeksData[3] + ", " + "금 : " + weeksData[4] + ", " + "토 : " + weeksData[5] + ", " + "일 : " + weeksData[6]);
                Log.i("FinalChecked", "isGoalChecked : " + isGoalChecked);
                Log.i("FinalChecked", "goalSet : " + goalSet);
                Log.i("FinalChecked", "isPreNotificationChecked : " + isPreNotificationChecked);
                Log.i("FinalChecked", "preNotificationTime : " + preNotificationTime);
                Log.i("FinalChecked", "isLocalNotificationChecked : " + isLocalNotificationChecked);
                Log.i("FinalChecked", "preNotifiationTime : " + latitude);
                Log.i("FinalChecked", "longitude : " + longitude);
                Log.i("FinalChecked", "placeName : " + placeName);



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

    public void setcdAddBtnToPreventBlurring() {
        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

        cdAddBtnItem.setBackgroundColor(0xFFC7DDFF);
        cdAddBtnItem2.setBackgroundColor(0xFFC7DDFF);

        cdAddBtnSub.setBackgroundColor(0xFFC7DDFF);
        cdAddBtnSub2.setBackgroundColor(0xFFC7DDFF);

    }

    void loadWorkTodayDataServer(){
        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.loadDataFromServer();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
               Log.i("loadWorkTodayData", response.body());

                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String no = jsonObject.getString("no");
                        String name = jsonObject.getString("name");
                        String nickName = jsonObject.getString("nickName");
                        String weeksDataJsonStr = jsonObject.getString("weeksDataJsonStr");
                        Gson gson = new Gson();
                        boolean[] weeksDatad = gson.fromJson(weeksDataJsonStr, boolean[].class);


                        String isGoalChecked = jsonObject.getString("isGoalChecked");
                        boolean isgoal = false;
                        if (isGoalChecked.equals("1")){
                            isgoal = true;
                        }else if (isGoalChecked.equals("0")){
                            isgoal = false;
                        }
                        String goalSet = jsonObject.getString("goalSet");

                        String isPreNotificationChecked = jsonObject.getString("isPreNotificationChecked");
                        boolean isprenoti = false;
                        if (isPreNotificationChecked.equals("1")){
                            isprenoti = true;
                        }else if(isPreNotificationChecked.equals("0")){
                            isprenoti = false;
                        }

                        String preNotificationTime = jsonObject.getString("preNotificationTime");
                        String isLocalNotificationChecked = jsonObject.getString("isLocalNotificationChecked");
                        boolean isLocal = false;
                        if (isLocalNotificationChecked.equals("1")){
                            isLocal = true;
                        }else if(isLocalNotificationChecked.equals("0")){
                            isLocal = false;
                        }

                        String placeName = jsonObject.getString("placeName");

                        String latitude = jsonObject.getString("latitude");
                        double lati = Double.parseDouble(latitude);

                        String longitude = jsonObject.getString("longitude");
                        double longi = Double.parseDouble(longitude);

                        String dstName = jsonObject.getString("dstName");
                        String path = "http://willd88.dothome.co.kr/YogaDesign/" + dstName;


                        workItems.add(0, new WorkItem(path, nickName, name, isgoal, goalSet, isprenoti, preNotificationTime, isLocal, placeName, weeksDatad));
                        adapter.notifyItemChanged(0);

                        Log.i("TAG1", no);
                        Log.i("TAG2", name);
                        Log.i("TAG3", nickName);
                        Log.i("TAG4", weeksDatad[0]+"");
                        Log.i("TAG5", isgoal+"");
                        Log.i("TAG6", goalSet);
                        Log.i("TAG7", isprenoti+"");
                        Log.i("TAG8", preNotificationTime);
                        Log.i("TAG9", isLocal+"");
                        Log.i("TAG11", lati+"");
                        Log.i("TAG12", longi+"");
                        Log.i("TAG13", path);
                        Log.i("TAG13", dstName);
                        Log.i("TAG", WorkTodayFragment.this.placeName);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("loadWorkTodayData", t.getMessage());
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkTodayDataServer();
    }
}
