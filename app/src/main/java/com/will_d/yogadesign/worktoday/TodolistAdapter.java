package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.RetrofitHelper;
import com.will_d.yogadesign.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        TodolistItem item = todolistItems.get(position);
        holder.nickName.setText(item.getNickName());
        holder.name.setText(item.getName());

        if (item.getIsGoalChecked()){
            if (item.getGoalSet().equals("하루에 1번")){

                holder.checked1.setVisibility(View.INVISIBLE);
                holder.checked2.setVisibility(View.INVISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
            }else if (item.getGoalSet().equals("하루에 2번")){

                holder.checked1.setVisibility(View.INVISIBLE);
                holder.checked2.setVisibility(View.VISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
            }else if (item.getGoalSet().equals("하루에 3번")){

                holder.checked1.setVisibility(View.VISIBLE);
                holder.checked2.setVisibility(View.VISIBLE);
                holder.checked3.setVisibility(View.VISIBLE);
            }

        }else {

            holder.checked1.setVisibility(View.INVISIBLE);
            holder.checked2.setVisibility(View.INVISIBLE);
            holder.checked3.setVisibility(View.VISIBLE);
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

        private boolean isChecked1 = false;
        private boolean isChecked2 = false;
        private boolean isChecked3 = false;
        private ImageView checked1;
        private ImageView checked2;
        private ImageView checked3;


        private TextView name;
        private CardView cdLog;

        private RelativeLayout rltodolistMissionComplete;

        public VH(@NonNull View itemView) {
            super(itemView);

            nickName = itemView.findViewById(R.id.nickName);
            cdTimeSet = itemView.findViewById(R.id.cd_timeSet);
            tvTimeSet = itemView.findViewById(R.id.tv_timeSet);

            checked1 = itemView.findViewById(R.id.checked_1);
            checked2 = itemView.findViewById(R.id.checked_2);
            checked3 = itemView.findViewById(R.id.checked_3);

            name = itemView.findViewById(R.id.name);
            cdLog = itemView.findViewById(R.id.cd_log);

            rltodolistMissionComplete = itemView.findViewById(R.id.rl_todolist_mission_complete);



//            loadWorkTodayDataServer();


            checked1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked1=!isChecked1;
                    if (isChecked1) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked1);
                    else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked1);
                    missionCompleteChecked();
                }
            });

            checked2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked2=!isChecked2;
                    if (isChecked2) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked2);
                    else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked2);
                    missionCompleteChecked();
                }
            });

            checked3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked3=!isChecked3;
                    if (isChecked3) Glide.with(context).load(R.drawable.ic_todolist_checked).into(checked3);
                    else Glide.with(context).load(R.drawable.ic_todolit_unchecked).into(checked3);

                    missionCompleteChecked();
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
                              String LogData = item.getEtTodolistLog().getText().toString();
                          }

                        }
                    });
                }
            });





        }

        public void WorkitemCounterLoadDB(){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            TodolistItem item = todolistItems.get(getAdapterPosition());
            Call<String> call = retrofitService.WorkItemCounterInsertDataDB(countNum+"", item.getNo());
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


        public void missionCompleteChecked(){
            TodolistItem item = todolistItems.get(getAdapterPosition());
            if (item.getIsGoalChecked()){
                if (item.getGoalSet().equals("하루에 1번")){
                    if (isChecked3) {
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
//                        countNum = (item.getCompleteNum() + 1);
//                        WorkitemCounterLoadDB();
                        Log.i("asdf23", "ddd");
                    }
                    else {
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        Log.i("asdf", "ddd");
                    }
                }else if (item.getGoalSet().equals("하루에 2번")){
                    if (isChecked3 && isChecked2) {
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
//                        countNum = (item.getCompleteNum() + 1);
//                        WorkitemCounterLoadDB();
                        Log.i("asdf23", "ddd");
                    }
                    else {
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        Log.i("asdf", "ddd");
                    }
                }else if (item.getGoalSet().equals("하루에 3번")){
                    if (isChecked3 && isChecked2 && isChecked1) {
                        rltodolistMissionComplete.setVisibility(View.VISIBLE);
//                        countNum = (item.getCompleteNum() + 1);
//                        WorkitemCounterLoadDB();
                        Log.i("asdf23", "ddd");
                    }
                    else{
                        rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                        Log.i("asdf", "ddd");
                    }
                }

            }else {
                if (isChecked3) {
                    rltodolistMissionComplete.setVisibility(View.VISIBLE);
//                    countNum = (item.getCompleteNum() + 1);
//                    WorkitemCounterLoadDB();
                    Log.i("asdf23", "ddd");
                }
                else {
                    rltodolistMissionComplete.setVisibility(View.INVISIBLE);
                    Log.i("asdf", "ddd");
                }
            }
        }
    }
}
