package com.will_d.yogadesign.worktoday.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.mainActivity.WorkShopActivity;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.worktoday.adapter.TodolistAdapter;
import com.will_d.yogadesign.worktoday.item.TodolistItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorkShopTodolistFragment extends Fragment {

    private TextView tvTodolistCurrentTime;

    private ArrayList<TodolistItem> todolistItems = new ArrayList<>();
    private TodolistAdapter adapter;
    private RecyclerView recyclerView;

    private RelativeLayout rlTodolistLogDialog;
    private EditText etTodolistLog;
    private TextView tvTodolistLogCancel;
    private TextView tvTodolistLogOk;

    private Boolean[] todolistBooleanStateInit = new Boolean[]{false, false, false};

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
        return inflater.inflate(R.layout.fragment_workshop_todolist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "ddd", false, "fff"));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));


        tvTodolistCurrentTime = view.findViewById(R.id.tv_todolist_current_time);
        rlTodolistLogDialog = view.findViewById(R.id.rl_todolist_log_dialog);
        etTodolistLog = view.findViewById(R.id.et_todolist_log);
        tvTodolistLogCancel = view.findViewById(R.id.tv_todolist_log_cancel);
        tvTodolistLogOk = view.findViewById(R.id.tv_todolist_log_ok);

        adapter = new TodolistAdapter(getActivity(), todolistItems);
        recyclerView = view.findViewById(R.id.todolist_recycler);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_todo);




        //???????????? ?????????
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = sdf.format(date);

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        String dayStr = "";

        switch (day_of_week){
            case 1: //???
                dayStr = "???";
                break;

            case 2: //???
                dayStr = "???";
                break;

            case 3: //???
                dayStr = "???";
                break;

            case 4: //???
                dayStr = "???";
                break;

            case 5: //???
                dayStr = "???";
                break;

            case 6: //???
                dayStr = "???";
                break;

            case 7: //???
                dayStr = "???";
                break;
        }

        tvTodolistCurrentTime.setText(getTime + ".(" + dayStr + ")");


        if(Global.workItemIndextNo!=null &&Global.workItems!=null){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String dayStr2 = sdf.format(date);
            String s = sharedPreferences.getString("dayCompairisonTodo", "");

            Log.i("zxcvb", s + " : " + dayStr2);
            WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();
            if (!(dayStr2.equals(s))){
                todolistItems.clear();
                adapter.notifyDataSetChanged();
                loading();
                insertWorkitemTodolistBooleanStateInitDB();
                editor.putString("dayCompairisonTodo", dayStr);
                editor.commit();
            }else if ((dayStr2.equals(s))&&workShopActivity.isCdTodolistClicked){
                todolistItems.clear();
                adapter.notifyDataSetChanged();
                loading();

                Global.workItemIndextNo.clear();
                for (int i = 0; i< Global.workItems.size(); i++){
                    Global.workItemIndextNo.add(Global.workItems.get(i).getNo());
                    Log.i("workitemPostion", Global.workItems.get(i).getNo());
                }
                Log.i("workitemPostion", " -------- ");

                Gson gson = new Gson();
                String workItemIndexJsonStr = gson.toJson(Global.workItemIndextNo);
                Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
                workItemPositionSetLoadToDB(workItemIndexJsonStr, Global.workItemIndextNo.size());

            }
            Log.i("asdfgh", !(dayStr2.equals(s))+"");
        }




    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (Global.workItemIndextNo!=null&&Global.workItems!=null){

            WorkShopActivity workShopActivity = (WorkShopActivity) getActivity();

            if (hidden){

            }else if (!hidden && workShopActivity.isCdTodolistClicked){
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String s = sharedPreferences.getString("dayCompairisonTodo", "");

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                String dayStr = sdf.format(date);

                if (!(dayStr.equals(s))){
                    todolistItems.clear();
                    adapter.notifyDataSetChanged();
                    loading();

                    insertWorkitemTodolistBooleanStateInitDB(); //????????? isModify??? ????????? ?????????
                    editor.putString("dayCompairisonTodo", dayStr);
                    editor.commit();
                }else {
                    todolistItems.clear();
                    adapter.notifyDataSetChanged();
                    loading();

                    Global.workItemIndextNo.clear();
                    for (int i = 0; i< Global.workItems.size(); i++){
                        Global.workItemIndextNo.add(Global.workItems.get(i).getNo());
                        Log.i("workitemPostion", Global.workItems.get(i).getNo());
                    }
                    Log.i("workitemPostion", " -------- ");

                    Gson gson = new Gson();
                    String workItemIndexJsonStr = gson.toJson(Global.workItemIndextNo);
                    Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
                    workItemPositionSetLoadToDB(workItemIndexJsonStr, Global.workItemIndextNo.size());
                }
                Log.i("asdfghKK", !(dayStr.equals(s))+"");

            }

        }




    }

    public void loading(){
        if (Global.workItems !=null&& Global.workItems.size()!=0){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }


    public void insertWorkitemTodolistBooleanStateInitDB(){
        Gson gson = new Gson();
        String todolistBooleanInitStr = gson.toJson(todolistBooleanStateInit);
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.insertWorkitemTodolistBooleanStateInitDB(todolistBooleanInitStr);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("asdf", response.body());

                Global.workItemIndextNo.clear();
                for (int i = 0; i< Global.workItems.size(); i++){
                    Global.workItemIndextNo.add(Global.workItems.get(i).getNo());
                    Log.i("workitemPostion", Global.workItems.get(i).getNo());
                }
                Log.i("workitemPostion", " -------- ");

                Gson gson = new Gson();
                String workItemIndexJsonStr = gson.toJson(Global.workItemIndextNo);
                Log.i("workItemIndexJsonStr", workItemIndexJsonStr);
                workItemPositionSetLoadToDB(workItemIndexJsonStr, Global.workItemIndextNo.size());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("asdf", t.getMessage());
            }
        });
    }

    public void loadWorkTodayDataServer(){
        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        Log.i("eee", id);

        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.TodolistLoadDataFromServer(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String jsonStr = response.body();
                Log.i("loadTodolistData", response.body());
//                todolistItems.clear();
//                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String no = jsonObject.getString("no");

                        String name = jsonObject.getString("name");

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

                        String cNum = jsonObject.getString("Completenum");
                        int completeNum = Integer.parseInt(cNum);

                        Gson gson2 = new Gson();
                        String todolitBooleanStateJsonStr = jsonObject.getString("todoistBooleanState");
                        boolean[] todolistBooleanState =gson2.fromJson(todolitBooleanStateJsonStr, boolean[].class);
                        Log.i("aaaa", "ddd");

                        String isDayOrToday = jsonObject.getString("isDayOrTodaySelected");
                        boolean[] isDayOrTodaySelected = gson.fromJson(isDayOrToday, boolean[].class);


                        String isl = jsonObject.getString("isLogModify");
                        boolean isLogModify = false;
                        if (isl.equals("1")){
                            isLogModify = true;
                        }else if (isl.equals("0")){
                            isLogModify = false;
                        }


                        String isTime = jsonObject.getString("isTimeFirst");
                        boolean isTimeFirst = false;
                        if (isTime.equals("1")){
                            isTimeFirst = true;
                        }else if(isTime.equals("0")){
                            isTimeFirst = false;
                        }




                        Calendar cal = Calendar.getInstance();
                        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
                        switch (day_of_week){
                            case 1://???
                                if (weeksData[6]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 2://???
                                if (weeksData[0]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 3://???
                                if (weeksData[1]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 4://???
                                if (weeksData[2]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 5://???
                                if (weeksData[3]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 6://???
                                if (weeksData[4]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 7://???
                                if (weeksData[5]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected, isLogModify, isTimeFirst));
                                    adapter.notifyItemChanged(0);
                                }
                                break;
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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
                loadWorkTodayDataServer();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }








}
