package com.will_d.yogadesign.worktoday.adapter;

import android.content.Context;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.worktoday.item.TodolistItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TodolistAdapter extends RecyclerView.Adapter<TodolistAdapter.VH> {
    private Context context;
    private ArrayList<TodolistItem> todolistItems;

    private ArrayList<String> nos = new ArrayList<>();
    private ArrayList<String> countNums = new ArrayList<>();

    private int countNum;
    int position;


    public TodolistAdapter(Context context, ArrayList<TodolistItem> todolistItems) {
        this.context = context;
        this.todolistItems = todolistItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_todolistitem, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final int finalPosition = position;

        TodolistItem item = todolistItems.get(position);
        boolean[] todolistBooleanState = item.getTodolistBooleanState();
        holder.isChecked3 = todolistBooleanState[2];
        holder.isChecked2 = todolistBooleanState[1];
        holder.isChecked1 = todolistBooleanState[0];

        holder.nickName.setText(item.getNickName());
        holder.name.setText(item.getName());
        Log.i("todolist", "" + todolistBooleanState[0] + todolistBooleanState[1] + todolistBooleanState[2]);
        if (item.getIsGoalChecked()){
            if (item.getGoalSet().equals("????????? 1???")){
                holder.disdiction = 1;
                holder.checked1.setVisibility(View.INVISIBLE);
                holder.checked2.setVisibility(View.INVISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
                if (todolistBooleanState[2]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked3);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked3);

                if (todolistBooleanState[2]) holder.rltodolistMissionComplete.setVisibility(View.VISIBLE);
                else holder.rltodolistMissionComplete.setVisibility(View.INVISIBLE);



            }else if (item.getGoalSet().equals("????????? 2???")){
                holder.disdiction = 2;
                holder.checked1.setVisibility(View.INVISIBLE);
                holder.checked2.setVisibility(View.VISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
                if (todolistBooleanState[2]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked3);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked3);
                if (todolistBooleanState[1]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked2);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked2);

                if (todolistBooleanState[2] && todolistBooleanState[1]) holder.rltodolistMissionComplete.setVisibility(View.VISIBLE);
                else holder.rltodolistMissionComplete.setVisibility(View.INVISIBLE);


            }else if (item.getGoalSet().equals("????????? 3???")){
                holder.disdiction = 3;
                holder.checked1.setVisibility(View.VISIBLE);
                holder.checked2.setVisibility(View.VISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
                if (todolistBooleanState[2]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked3);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked3);
                if (todolistBooleanState[1]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked2);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked2);
                if (todolistBooleanState[0]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked1);
                else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked1);


                if (todolistBooleanState[2] && todolistBooleanState[1] && todolistBooleanState[0]) holder.rltodolistMissionComplete.setVisibility(View.VISIBLE);
                else holder.rltodolistMissionComplete.setVisibility(View.INVISIBLE);
            }

        }else {
            holder.disdiction = 0;
            holder.checked1.setVisibility(View.INVISIBLE);
            holder.checked2.setVisibility(View.INVISIBLE);
            holder.checked3.setVisibility(View.VISIBLE);
            if (todolistBooleanState[2]) Glide.with(context).load(R.drawable.ic_todolist_checked).into(holder.checked3);
            else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(holder.checked3);

            if (todolistBooleanState[2]) holder.rltodolistMissionComplete.setVisibility(View.VISIBLE);
            else holder.rltodolistMissionComplete.setVisibility(View.INVISIBLE);
        }

        boolean[] isDayOrTodaySelected = item.getIsDayOrTodaySelected();
        if (!isDayOrTodaySelected[0] && isDayOrTodaySelected[1]){
            holder.cdTodayWork.setVisibility(View.VISIBLE);
        }else {
            holder.cdTodayWork.setVisibility(View.INVISIBLE);
        }

        if(item.getIsLogModify()){
            holder.cdLog.setVisibility(View.INVISIBLE);
            holder.cdLogModify.setVisibility(View.VISIBLE);
        }else {
            holder.cdLog.setVisibility(View.VISIBLE);
            holder.cdLogModify.setVisibility(View.INVISIBLE);
        }






    }

    @Override
    public int getItemCount() {
        return todolistItems.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView nickName;

        private CardView cdTimeSet;
        private TextView tvTimeSet;

        private int disdiction;

        private boolean isChecked1 = false;
        private boolean isChecked2 = false;
        private boolean isChecked3 = false;
        private ImageView checked1;
        private ImageView checked2;
        private ImageView checked3;


        private TextView name;
        //??????
        private CardView cdLog;
        private CardView cdLogModify;

        private boolean isLogModify = false;

        private RelativeLayout rltodolistMissionComplete;
        private TextView tvMissionClearTime;

        private CardView cdTodayWork;


        public VH(@NonNull View itemView) {
            super(itemView);

            nickName = itemView.findViewById(R.id.nickName);
            cdTimeSet = itemView.findViewById(R.id.cd_timeSet);
            tvTimeSet = itemView.findViewById(R.id.tv_timeSet);

            checked1 = itemView.findViewById(R.id.checked_1);
            checked2 = itemView.findViewById(R.id.checked_2);
            checked3 = itemView.findViewById(R.id.checked_3);

            name = itemView.findViewById(R.id.name);

            //??????
            cdLog = itemView.findViewById(R.id.cd_log);
            cdLogModify = itemView.findViewById(R.id.cd_log_modify);

            rltodolistMissionComplete = itemView.findViewById(R.id.rl_todolist_mission_complete);
            tvMissionClearTime = itemView.findViewById(R.id.tv_mission_clear_time);


            cdTodayWork = itemView.findViewById(R.id.cd_today_work);

//            loadWorkTodayDataServer();


            checked1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isChecked3){
                        isChecked1=!isChecked1;
                        TodolistItem item = todolistItems.get(getAdapterPosition());
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[0] = isChecked1;
                        Log.i("TAG2222", todolistBooleanState[0] + "");
                        if (isChecked1) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked1);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked1);
                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                    }else {
                        return;
                    }

                }
            });


            checked2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isChecked3){
                        isChecked2=!isChecked2;
                        TodolistItem item = todolistItems.get(getAdapterPosition());
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[1] = isChecked2;
                        if (isChecked2) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked2);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked2);
                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                    }else {
                        return;
                    }
                }
            });


            checked3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodolistItem item = todolistItems.get(getAdapterPosition());
                    if (isChecked1 && isChecked2 && !isChecked3 && disdiction==3){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()+1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(0, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);//????????? 0?????? ???????????? 1?????? ????????????
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"gggg");
                    }else if (isChecked1 && isChecked2 && isChecked3 && disdiction==3){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()-1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(1, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"ggg");
                    }else if (isChecked2 && !isChecked3 && disdiction==2){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()+1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(0, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"");
                    }else if (isChecked2 && isChecked3 && disdiction==2){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()-1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(1, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"qfqwf");
                    } else if (isChecked3 && disdiction == 1){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()-1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(1, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"ss");

                    } else if (!isChecked3 && disdiction ==1){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()+1);
                        countNum =item.getCompleteNum();

                        todolistCalendarInsertDB(0, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"ssss");

                    }  else if (isChecked3 && disdiction == 0){
                        isChecked3=!isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum()-1);

                        todolistCalendarInsertDB(1, item.getNo());

                        countNum =item.getCompleteNum();
                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum+"kk");

                    } else if (!isChecked3 && disdiction == 0) {
                        isChecked3 = !isChecked3;
                        boolean[] todolistBooleanState = item.getTodolistBooleanState();
                        todolistBooleanState[2] = isChecked3;
                        if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                        else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);
                        item.setCompleteNum(item.getCompleteNum() + 1);
                        countNum = item.getCompleteNum();

                        todolistCalendarInsertDB(0, item.getNo());

                        workitemTodolistBooleanStateInsertDB(item);
                        missionCompleteChecked(getAdapterPosition());
                        Log.i("TAG", countNum + "kkkk");
                    } else {
                        Log.i("TAG", item.getCompleteNum()+"da");
                        return;
                    }



                }
            });







            cdLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodolistItem item = todolistItems.get(getAdapterPosition());
                    item.getRlTodolistLogDialog().setVisibility(View.VISIBLE);

                    item.getTvTodolistLogCancel().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                            item.getEtTodolistLog().setText("");
                        }
                    });

                    item.getTvTodolistLogOk().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (item.getEtTodolistLog().getText().toString().equals("")){
                                item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                                item.getEtTodolistLog().setText("");
                            }else {
                                String logData = item.getEtTodolistLog().getText().toString();

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
                                String days = getTime + ".(" + dayStr + ")";

                                isLogModify = true;
                                if (item.getIsTimeFirst()){ //true??? first??? ???????????? ??????
                                    todolistLogDataInsertDB(1 ,item.getNo(), days, logData, isLogModify);
                                }else {
                                    todolistLogDataInsertDB(0 ,item.getNo(), days, logData, isLogModify);
                                }
                                item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                                item.getEtTodolistLog().setText("");

                                cdLog.setVisibility(View.INVISIBLE);
                                cdLogModify.setVisibility(View.VISIBLE);

                                Log.i("TagTest", item.getNo() + " : "  + days + " : " + logData);

                            }

                        }
                    });
                }
            });

            cdLogModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodolistItem item = todolistItems.get(getAdapterPosition());
                    item.getRlTodolistLogDialog().setVisibility(View.VISIBLE);

                    item.getTvTodolistLogCancel().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                            item.getEtTodolistLog().setText("");
                        }
                    });

                    item.getTvTodolistLogOk().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (item.getEtTodolistLog().getText().toString().equals("")){
                                item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                                item.getEtTodolistLog().setText("");
                            }else {
                                String logData = item.getEtTodolistLog().getText().toString();

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
                                String days = getTime + ".(" + dayStr + ")";

                                isLogModify = true;
                                todolistLogDataInsertDB(1, item.getNo(), days, logData, isLogModify);


                                item.getRlTodolistLogDialog().setVisibility(View.INVISIBLE);
                                item.getEtTodolistLog().setText("");

                                cdLog.setVisibility(View.INVISIBLE);
                                cdLogModify.setVisibility(View.VISIBLE);

                                Log.i("TagTest", item.getNo() + " : "  + days + " : " + logData);

                            }

                        }
                    });



                }
            });


        }


        //????????? ????????? ????????? ???????????? ?????????.
        public void todolistCalendarInsertDB(int idnetifier, String workItemNO){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            long now = System.currentTimeMillis();
            Date date = new Date(now);

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy:MM:dd");
            String getTiem = simpleDate.format(date);

            Call<String> call = retrofitService.todolistCalendarInsertDB(idnetifier, workItemNO, getTiem);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                   Log.i("retrofitCalendar", response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("retrofitCalendar", t.getMessage());
                }
            });


        }



        //log????????? ?????? ????????? ????????? ???????????? 0??? ????????? ?????????
        //????????? ?????? ???????????? 1??? ??????????????? ????????? ????????? ????????? ???????????? ????????????
        //????????? ????????? 0?????? ?????? ?????????????????? ?????? ????????? ???????????? ?????? ????????? ???????????????.

        //log??? ???????????? ????????????
        //Identifier : ????????? 0?????? ?????????????????? 1?????? ???????????? ?????????
        public void todolistLogDataInsertDB(int identifier, String workItemNo, String days, String log, boolean isLogModify){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.todolistLogDataInsertDB(identifier, workItemNo, days, log, isLogModify);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("Tag", response.body());


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("Tag", t.getMessage());


                }
            });
        }




//        Gson gson = new Gson();
//        String todolistBooleanJsonStr = gson.toJson(todolistBooleanState);

        public void workitemTodolistBooleanStateInsertDB(TodolistItem item){
            boolean[] todolistBooleanState =item.getTodolistBooleanState();
            Gson gson = new Gson();
            String todolistBooleanStr = gson.toJson(todolistBooleanState);
            Log.i("TAG2222", todolistBooleanStr);
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Log.i("TAG2222", item.getNo());
            Call<String> call = retrofitService.WorkItemTodolistBooleanStateInsertDB(todolistBooleanStr, item.getNo());
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

        public void WorkitemCounterLoadDB(int position){ //????????? ????????????
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            TodolistItem item = todolistItems.get(position);
            Call<String> call = retrofitService.WorkItemCounterInsertDataDB(countNum+"", item.getNo());
            Log.i("TAG213", countNum+"");
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


        public void missionCompleteChecked(int position){
            TodolistItem item = todolistItems.get(position);
            if (item.getIsGoalChecked()){
                if (item.getGoalSet().equals("????????? 1???")){
                    if (isChecked3) {
                        missionClearTimeSet();
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
                        WorkitemCounterLoadDB(position);

                        Log.i("asdf23", "ddd");
                    }
                    else {
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        WorkitemCounterLoadDB(position);
                        Log.i("asdf", "ddd");
                    }
                }else if (item.getGoalSet().equals("????????? 2???")){
                    if (isChecked3 && isChecked2) {
                        missionClearTimeSet();
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
                        WorkitemCounterLoadDB(position);
                        Log.i("asdf23", "ddd");
                    }
                    else {
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        WorkitemCounterLoadDB(position);
                        Log.i("asdf", "ddd");
                    }
                }else if (item.getGoalSet().equals("????????? 3???")){
                    if (isChecked3 && isChecked2 && isChecked1) {
                        missionClearTimeSet();
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
                        WorkitemCounterLoadDB(position);
                        Log.i("asdf23", "ddd");
                    }
                    else{
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        WorkitemCounterLoadDB(position);
                        Log.i("asdf", "ddd");
                    }
                }

            }else {
                if (isChecked3) {
                    missionClearTimeSet();
                    rltodolistMissionComplete.setVisibility(View.VISIBLE);
                    WorkitemCounterLoadDB(position);
                    Log.i("asdf23", "ddd");
                }
                else {
                    rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                    WorkitemCounterLoadDB(position);
                    Log.i("asdf", "ddd");
                }
            }
        }//missionComplete method....


        public void missionClearTimeSet(){
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String getTime = sdf.format(date);
            tvMissionClearTime.setText(getTime);
        }



    }// class ViewHolder....


}// TodolistAdapter class....
