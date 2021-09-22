package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.RetrofitHelper;
import com.will_d.yogadesign.RetrofitService;

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

public class WorkShopTodolistFragment extends Fragment {

    TextView tvTodolistCurrentTime;

    ArrayList<TodolistItem> todolistItems = new ArrayList<>();
    TodolistAdapter adapter;
    RecyclerView recyclerView;

    RelativeLayout rlTodolistLogDialog;
    EditText etTodolistLog;
    TextView tvTodolistLogCancel;
    TextView tvTodolistLogOk;


    private Boolean[] todolistBooleanStateInit = new Boolean[]{false, false, false};

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

        //Todo: 아이템 체크된 상태 날짜 구해서 초기화 해줘야하는데 어떻게 해줘야할지 모르겠다.


        //현재시간 구하기
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = sdf.format(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String getTimeTime = sdf2.format(date);
        Log.i("TAGG", getTimeTime);

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        String dayStr = "";


        //todo: 이거 논리가 잘못됨 시간의 차이로 구해야함 일단은 다른거하고 하자
        switch (day_of_week){
            case 1: //일
                dayStr = "일";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 2: //일
                dayStr = "월";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 3: //일
                dayStr = "화";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 4: //일
                dayStr = "수";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 5: //일
                dayStr = "목";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 6: //일
                dayStr = "금";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;

            case 7: //일
                dayStr = "토";
                if (getTimeTime.equals("23:59:00")) insertWorkitemTodolistBooleanStateInitDB();
                break;
        }

        tvTodolistCurrentTime.setText(getTime + ".(" + dayStr + ")");






    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkTodayDataServer();
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
                todolistItems.clear();
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

                        Calendar cal = Calendar.getInstance();
                        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
                        switch (day_of_week){
                            case 1://일
                                if (weeksData[6]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 2://월
                                if (weeksData[0]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 3://화
                                if (weeksData[1]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 4://수
                                if (weeksData[2]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 5://목
                                if (weeksData[3]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 6://금
                                if (weeksData[4]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
                                    adapter.notifyItemChanged(0);
                                }
                                break;

                            case 7://토
                                if (weeksData[5]){
                                    todolistItems.add(0,new TodolistItem(no, completeNum, name, nickName, isGoalChecked, goalSet, rlTodolistLogDialog, etTodolistLog, tvTodolistLogCancel, tvTodolistLogOk, todolistBooleanState, isDayOrTodaySelected));
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
}
