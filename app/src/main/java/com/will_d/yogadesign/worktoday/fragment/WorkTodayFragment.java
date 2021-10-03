package com.will_d.yogadesign.worktoday.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.mainActivity.WorkShopActivity;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.worktoday.ItemTouchHelperCallback;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.worktoday.activity.WokrDataSetActivity;
import com.will_d.yogadesign.worktoday.adapter.WorkRecyclerAdapter;
import com.will_d.yogadesign.worktoday.item.WorkItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import soup.neumorphism.NeumorphCardView;

import static android.content.Context.MODE_PRIVATE;

public class WorkTodayFragment extends Fragment {

    private ArrayList<String> workItemIndextNo = new ArrayList<>();

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

    private ProgressBar progressBar;

    private boolean isFirst = false;
    public static boolean isWorkItemAdd = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
    }

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

        progressBar = view.findViewById(R.id.progress_today);


        setcdAddBtnToPreventBlurring();

        cdAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WokrDataSetActivity.class);
                activityResultLauncher.launch(intent);
                getActivity().overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);


            }
        });


        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String dayStr = sdf.format(date);

        SharedPreferences pref = getActivity().getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String str = pref.getString("dayCompairison", "");

        //todo: 일단 이기능이 제대로 동작을 안함.
        if (!(dayStr.equals(str))){
            workItems.clear();
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            workItemOnedayUpdateDB(); //여기서 isModify도 초기화 해주자 istimeFirst도 초기화해주자
            editor.putString("dayCompairison", dayStr);
            editor.commit();
        }else {
            workItems.clear();
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loadWorkTodayDataServer();
        }

        Log.i("asdfg", !(dayStr.equals(str))+"");


        if (workItems.size()==0){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onResume() {
        super.onResume();

        WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();
        if (!(workShopActivity.isCdTodolistClicked)&& isFirst){
            workItemIndextNo.clear();
            for (int i=0; i<workItems.size(); i++){
                workItemIndextNo.add(workItems.get(i).getNo());
                Log.i("workitemPostion", workItems.get(i).getNo());
            }
            Log.i("workitemPostion", " -------- ");

            Gson gson = new Gson();
            String workItemIndexJsonStr = gson.toJson(workItemIndextNo);
            Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
            workItemPositionSetLoadToDB(workItemIndexJsonStr, workItemIndextNo.size());
            Global.workItemIndextNo = workItemIndextNo;
            isFirst = false;
        }



        Log.i("nmn", isWorkItemAdd + "");
        if (isWorkItemAdd){
            workItems.clear();
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loadWorkTodayDataServer();
            isWorkItemAdd =false;
        }


        if (workItems.size()==0){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //숨겨지면 true
        //다시 나타나면 false
        WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();
        if (hidden && !(workShopActivity.isCdTodolistClicked)){
            workItemIndextNo.clear();
            for (int i=0; i<workItems.size(); i++){
                workItemIndextNo.add(workItems.get(i).getNo());
                Log.i("workitemPostion", workItems.get(i).getNo());
            }
            Log.i("workitemPostion", " -------- ");

            Gson gson = new Gson();
            String workItemIndexJsonStr = gson.toJson(workItemIndextNo);
            Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
            workItemPositionSetLoadToDB(workItemIndexJsonStr, workItemIndextNo.size());
            Global.workItemIndextNo = workItemIndextNo;

        }else if(!hidden){
            long now = System.currentTimeMillis();
            Date date = new Date(now);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String dayStr = sdf.format(date);

            SharedPreferences pref = getActivity().getSharedPreferences("Data", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            String str = pref.getString("dayCompairison", "");
            //todo: 일단 이기능이 제대로 동작을 안함.
            if (!(dayStr.equals(str))){
                workItems.clear();
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                workItemOnedayUpdateDB();
                editor.putString("dayCompairison", dayStr);
                editor.commit();
            }else {
                workItems.clear();
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                loadWorkTodayDataServer();
            }


        }

        if (workItems.size()==0){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        //강제종료를 하거나 뒤로가기를 눌러서 끄면 onStop()이 발동
//        workItemIndextNo.clear();
//        for (int i=0; i<workItems.size(); i++){
//            workItemIndextNo.add(workItems.get(i).getNo());
//            Log.i("workitemPostion", workItems.get(i).getNo());
//        }
//        Log.i("workitemPostion", " -------- ");
//
//        Gson gson = new Gson();
//        String workItemIndexJsonStr = gson.toJson(workItemIndextNo);
//        Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
//        workItemPositionSetLoadToDB(workItemIndexJsonStr, workItemIndextNo.size());
    }

    public void setcdAddBtnToPreventBlurring() {
        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

    }



    public void loadWorkTodayDataServer(){
        SharedPreferences pref = getActivity().getSharedPreferences("Data", MODE_PRIVATE);
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

                        String isLogModi = jsonObject.getString("isLogModify");
                        boolean isLogModify = false;
                        if (isLogModi.equals("1")){
                            isLogModify = true;
                        }else if(isLogModi.equals("0")){
                            isLogModify = false;
                        }

                        String isTime = jsonObject.getString("isTimeFirst");
                        boolean isTimeFirst = false;
                        if (isTime.equals("1")){
                            isTimeFirst = true;
                        }else if(isTime.equals("0")){
                            isTimeFirst = false;
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                        workItems.add(0, new WorkItem(no, imgUrl, nickName, name, isGoalChecked, goalSet, isPreNotificationChecked, preNotificationTime, isLocalNotificationChecked, placeName, weeksData, isItemOnOff, completeNum, isDayOrTodaySelected, rlWorkitemDeleteDialog, tvWorkitemDeleteOK, tvWorkitemDeleteCancel, isLogModify, isTimeFirst));
                        adapter.notifyItemChanged(0);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                    Global.workItems = workItems;



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


    public void workItemOnedayUpdateDB(){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.workItemOnedayUpdateDB(false);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAG", response.body());
                loadWorkTodayDataServer();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });

    }


    public void workItemPositionSetLoadToDB(String workItemIndexJsonStr, int workItemIndexSize){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.workItemPositionSetLoadToDB(workItemIndexJsonStr, workItemIndexSize);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

                Log.i("mnm", isWorkItemAdd+"");

        }
    });

}
