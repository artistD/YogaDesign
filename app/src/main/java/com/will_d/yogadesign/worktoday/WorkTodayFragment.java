package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.will_d.yogadesign.G;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.RetrofitHelper;
import com.will_d.yogadesign.RetrofitService;
import com.will_d.yogadesign.WorkShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import soup.neumorphism.NeumorphCardView;

public class WorkTodayFragment extends Fragment {

    private ArrayList<WorkItem> workItems = new ArrayList<>();
    //**********
    private RecyclerView recyclerView;
    private WorkRecyclerAdapter adapter;
    ItemTouchHelper helper;

    private NeumorphCardView cdAddBtn;

    private RelativeLayout rlWorkitemDeleteDialog;
    private TextView tvWorkitemDeleteOK;
    private TextView tvWorkitemDeleteCancel;

    private NeumorphCardView cdAddBtn2;
//
//    private NeumorphCardView cdAddBtnItem;
//    private NeumorphCardView cdAddBtnItem2;
//    private NeumorphCardView cdAddBtnSub;
//    private NeumorphCardView cdAddBtnSub2;
//
//    private boolean isclick = false;
//    private RelativeLayout rlBlur;
//
//    private WorkShopActivity workShopActivity;
//
//    private Animation ani;
//    private Animation ani2;
//    private Animation ani3;
//    private Animation ani4;


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

        rlWorkitemDeleteDialog = view.findViewById(R.id.rl_workitem_delete_dialog);
        tvWorkitemDeleteOK = view.findViewById(R.id.tv_workitem_delete_ok);
        tvWorkitemDeleteCancel = view.findViewById(R.id.tv_workitem_delete_cancel);
        cdAddBtn2 = view.findViewById(R.id.cd_addbtn2);
//
//        cdAddBtnItem = view.findViewById(R.id.cd_addbtn_item);
//        cdAddBtnItem2 = view.findViewById(R.id.cd_addbtn_item2);
//
//        cdAddBtnSub = view.findViewById(R.id.cd_addbtn_sub);
//        cdAddBtnSub2 = view.findViewById(R.id.cd_addbtn_sub2);
//
//
//
//        ani = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_fade);
//        ani2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_fade_end);
//        ani3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_layout_fade);
//        ani4 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_layout_fade_end);
//
//        rlBlur = view.findViewById(R.id.rl_Blur);
//        workShopActivity = (WorkShopActivity)getActivity();


        setcdAddBtnToPreventBlurring();

        cdAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WokrDataSetActivity.class);
                //todo:StackOverFlowError, OutOfMemory 에러
//                Gson gson = new Gson();
//                String jsonStr = gson.toJson(workItems);
//                intent.putExtra("Workitems", jsonStr);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);

            }
        });



        //Todo: 나중에 버튼으로 더 추가적인 작업을 하기위한 주석임....... 필요하면 나중에 쓰라고

//        cdAddBtn.setOnClickListener(new View.OnClickListener() {
//            WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();
//            @Override
//            public void onClick(View v) {
//                isclick=!isclick;
//                if (isclick) {
//                    cdAddBtnBeginning();
//                    workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.VISIBLE);
//                    workShopActivity.getMeterialCdTodolist().setCardElevation(0);
//                    cdAddBtnItem.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), WokrDataSetActivity.class);
//                            Gson gson = new Gson();
//                            String jsonstr = gson.toJson(workItems);
//                            Log.i("TAG", jsonstr);
//                            intent.putExtra("Workitems", jsonstr);
//
//                            activityResultLauncher.launch(intent);
//                            getActivity().overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);
//                            cdAddBtnEnd();
//                            workShopActivity.getMeterialCdTodolist().setCardElevation(4);
//                            workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.INVISIBLE);
//                            isclick=false;
//                        }
//                    });
//
//                    cdAddBtnSub.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//
//
//                }
//                else {
//                    cdAddBtnEnd();
//                    workShopActivity.getMeterialCdTodolist().setCardElevation(4);
//                    workShopActivity.getMeterialIvTodolistBlur().setVisibility(View.INVISIBLE);
//                }
//
//            }
//        });
//
//        setcdAddBtnToPreventBlurring();

    }

//    public void cdAddBtnBeginning(){
//        cdAddBtnItem.startAnimation(ani);
//        cdAddBtnItem2.startAnimation(ani);
//        cdAddBtnSub.startAnimation(ani);
//        cdAddBtnSub2.startAnimation(ani);
//
//        cdAddBtnItem.setVisibility(View.VISIBLE);
//        cdAddBtnItem2.setVisibility(View.VISIBLE);
//        cdAddBtnSub.setVisibility(View.VISIBLE);
//        cdAddBtnSub2.setVisibility(View.VISIBLE);
//
//
//        workShopActivity.getViewLine().startAnimation(ani3);
//        workShopActivity.getIvBnvBlur().startAnimation(ani3);
//        rlBlur.startAnimation(ani3);
//        workShopActivity.getToolbarBlur().startAnimation(ani3);
//
//        workShopActivity.getToolbarBlur().setVisibility(View.VISIBLE);
//        workShopActivity.getViewLine().setVisibility(View.INVISIBLE);
//        workShopActivity.getIvBnvBlur().setVisibility(View.VISIBLE);
//        rlBlur.setVisibility(View.VISIBLE);
//    }
//
//    public void cdAddBtnEnd(){
//        cdAddBtnItem.startAnimation(ani2);
//        cdAddBtnSub.startAnimation(ani2);
//
//        cdAddBtnItem.setVisibility(View.INVISIBLE);
//        cdAddBtnItem2.setVisibility(View.INVISIBLE);
//        cdAddBtnSub.setVisibility(View.INVISIBLE);
//        cdAddBtnSub2.setVisibility(View.INVISIBLE);
//
//        workShopActivity.getViewLine().startAnimation(ani4);
//        workShopActivity.getIvBnvBlur().startAnimation(ani4);
//        rlBlur.startAnimation(ani4);
//        workShopActivity.getToolbarBlur().startAnimation(ani4);
//
//        workShopActivity.getToolbarBlur().setVisibility(View.INVISIBLE);
//        rlBlur.setVisibility(View.INVISIBLE);
//        workShopActivity.getViewLine().setVisibility(View.VISIBLE);
//        workShopActivity.getIvBnvBlur().setVisibility(View.INVISIBLE);
//
//    }
//
    public void setcdAddBtnToPreventBlurring() {
        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

//        cdAddBtnItem.setBackgroundColor(0xFFC7DDFF);
//        cdAddBtnItem2.setBackgroundColor(0xFFC7DDFF);
//
//        cdAddBtnSub.setBackgroundColor(0xFFC7DDFF);
//        cdAddBtnSub2.setBackgroundColor(0xFFC7DDFF);

    }

    public void loadWorkTodayDataServer(){
        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        Log.i("확인!!제발", id);
        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.WorkItemLoadDataFromServer(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String jsonStr = response.body();
                Log.i("loadWorkTodayData2", response.body());
               workItems.clear();
                try {

                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String no = jsonObject.getString("no");
                        String id =jsonObject.getString("id");
                        String name = jsonObject.getString("name");

                        String dstName = jsonObject.getString("dstName");
                        String imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/workitem/" + dstName;


                        String nickName = jsonObject.getString("nickName");

                        String weeksDataJsonStr = jsonObject.getString("weeksData");
                        Gson gson = new Gson();
                        boolean[] weeksData = gson.fromJson(weeksDataJsonStr, boolean[].class);

                        String isg = jsonObject.getString("isGoalChecked");
                        boolean isGoalChecked = false;
                        if (isg.equals("1")){
                            isGoalChecked = true;
                        }else if (isg.equals("0")){
                            isGoalChecked = false;
                        }

                        String goalSet = jsonObject.getString("goalSet");

                        String isPre = jsonObject.getString("isPreNotificationChecked");
                        boolean isPreNotificationChecked = false;
                        if (isPre.equals("1")){
                            isPreNotificationChecked = true;
                        }else if(isPre.equals("0")){
                            isPreNotificationChecked = false;
                        }
                        String preNotificationTime = jsonObject.getString("preNotificationTime");

                        String isLocal = jsonObject.getString("isLocalNotificationChecked");
                        boolean isLocalNotificationChecked = false;
                        if (isLocal.equals("1")){
                            isLocalNotificationChecked = true;
                        }else if(isLocal.equals("0")){
                            isLocalNotificationChecked = false;
                        }

                        String placeName = jsonObject.getString("placeName");

                        String lati = jsonObject.getString("latitude");
                        double latitude = Double.parseDouble(lati);

                        String longi = jsonObject.getString("longitude");
                        double longitude = Double.parseDouble(longi);
                        String isItem = jsonObject.getString("isItemOnOff");
                        boolean isItemOnOff = false;
                        if (isItem.equals("1")){
                            isItemOnOff = true;
                        }else if(isItem.equals("0")){
                            isItemOnOff = false;
                        }

                        String isItemP = jsonObject.getString("isItemPublic");
                        boolean isItemPublic = false;
                        if (isItemP.equals("1")){
                            isItemPublic = true;
                        }else if(isItemP.equals("0")){
                            isItemPublic = false;
                        }

                        String cNum = jsonObject.getString("Completenum");
                        int completeNum = Integer.parseInt(cNum);
                        Log.i("dddddd", "dqwqdw");

                        String isDayOrToday = jsonObject.getString("isDayOrTodaySelected");
                        boolean[] isDayOrTodaySelected = gson.fromJson(isDayOrToday, boolean[].class);

                        String now = jsonObject.getString("now");

//                        insertWorkitemSortationNumber(no);

                        workItems.add(0, new WorkItem(no, imgUrl, nickName, name, isGoalChecked, goalSet, isPreNotificationChecked, preNotificationTime, isLocalNotificationChecked, placeName, weeksData, isItemOnOff, completeNum, isDayOrTodaySelected, rlWorkitemDeleteDialog, tvWorkitemDeleteOK, tvWorkitemDeleteCancel));
                        adapter.notifyItemChanged(0);

                    }

                    G.workItems = workItems;


                } catch (JSONException e) {
                    Log.i("왜 와이", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("왜 와이", t.getMessage());
            }
        });


    }


    //todo: 하루전용 아이템은 그다음날이되면 자동으로 switch가 꺼져야함 그것을 레트로핏으로 구현해야......


    //todo: 아이템 이동변
    public void insertWorkitemSortationNumber(String no){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.insertWorkitemSortationNumber(no);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAGddd", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAGddd", "Errror");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkTodayDataServer();

    }

}
