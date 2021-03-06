package com.will_d.yogadesign.time.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.mainActivity.WorkShopActivity;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.time.fragment.TimeTimerFragment;
import com.will_d.yogadesign.time.item.TimerItem;
import com.will_d.yogadesign.worktoday.item.WorkItem;

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

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.VH> {

    private Context context;
    private ArrayList<TimerItem> items;
    private RelativeLayout rlClickSaveDialog;
    private TextView tvTime;
    private boolean isLogModify = false;
    private boolean isTimeFirst = false;

    private TimeTimerFragment.TimeThread timeThread;


    WorkShopActivity workShopActivity;
    TimeTimerFragment timeTimerFragment;


    public TimerAdapter(Context context, ArrayList<TimerItem> items, RelativeLayout rlClickSaveDialog, TextView tvTime) {
        this.context = context;
        this.items = items;
        this.rlClickSaveDialog = rlClickSaveDialog;
        this.tvTime = tvTime;
    }

    public void setTimeThread(TimeTimerFragment.TimeThread timeThread) {
        this.timeThread = timeThread;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_timeritem, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TimerItem item = items.get(position);
        holder.tv.setText(item.getNickName());


        workShopActivity = (WorkShopActivity) context;
        timeTimerFragment= (TimeTimerFragment) workShopActivity.fragments[1];

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

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerItem timerItem = items.get(holder.getAdapterPosition());
                for (int i=0; i<Global.workItems.size(); i++){
                    WorkItem workItem = Global.workItems.get(i);
                    Log.i("timeTest5", item.getWorkItemNo());
                    if (workItem.getNo().equals(timerItem.getWorkItemNo())){
                        Log.i("timeTest4", workItem.getNo());
                        isLogModify = workItem.getIsLogModify();
                        isTimeFirst = workItem.getIsTimeFirst();
                        Log.i("timeTest3", isTimeFirst + "");
                        break;
                    }else {
                        continue;
                    }
                }

                if (timeThread !=null){
                    if (isLogModify){   // ???????????? ???????????? ???????????????
                        Log.i("timeTest2", isTimeFirst + "");
                        if (isTimeFirst){
                            //????????? ??????????????? ????????? ??????
                            Log.i("getWorkItemNoA", item.getWorkItemNo() + ":" + days);
                            holder.timeDataGetDB(item.getWorkItemNo(), days);
                            loading();
                            Toast.makeText(context, "????????? ??????????????????. ????????? ??????????????????", Toast.LENGTH_SHORT).show();

                        }else {
                            String time = String.format("%02d:%02d", timeThread.time, timeThread.min);
                            holder.timeDataInserOrUpdatetDB(1, item.getWorkItemNo(), days, time, true);
                            loading();
                            Toast.makeText(context, "????????? ??????????????????. ????????? ??????????????????", Toast.LENGTH_SHORT).show();

                        }

                    }else { //????????? ???????????? ?????????
                        if (isTimeFirst) { //true??? ????????? ???????????? ???
                            //????????? ???????????? ???????????? ???????????????
                            //?????? ???????????? ??????????????? ??????! ????????? ?????????????????? ?????? ????????? ????????????
                            //????????? ????????? ??????????????? ????????? ???????????????
                            Log.i("getWorkItemNoA", item.getWorkItemNo() + ":" + days);
                            holder.timeDataGetDB(item.getWorkItemNo(), days);
                            loading();
                            Toast.makeText(context, "????????? ??????????????????. ????????? ??????????????????", Toast.LENGTH_SHORT).show();

                        }else {
                            Log.i("getWorkItemNoB", item.getWorkItemNo() + ":" + days);
                            String time = String.format("%02d:%02d", timeThread.time, timeThread.min);
                            holder.timeDataInserOrUpdatetDB(0, item.getWorkItemNo(), days, time, true);
                            loading();
                            Toast.makeText(context, "????????? ??????????????????. ????????? ??????????????????", Toast.LENGTH_SHORT).show();

                        }
                    }

                }else {
                    Toast.makeText(context, "???????????? ?????? ????????? ????????????!", Toast.LENGTH_SHORT).show();
                }






            }
        });

    }

    public void loading(){
        workShopActivity.getIvBnvBlur().setVisibility(View.VISIBLE);
        rlClickSaveDialog.setVisibility(View.INVISIBLE);
        timeTimerFragment.rl.setVisibility(View.VISIBLE);
        timeTimerFragment.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VH extends RecyclerView.ViewHolder {//################################################################
        private TextView tv;
        private CardView cd;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            cd = itemView.findViewById(R.id.cd);

        }

        //identifier
        //0?????? ??????????????????
        //1?????? ????????????????????????
        public void timeDataInserOrUpdatetDB(int identifier, String workItemNo, String day, String time, boolean isTimeFirst){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.timeDataInserOrUpdatetDB(identifier, workItemNo, day, time, isTimeFirst);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                    //todo: ????????? ???????????? ???????????? ???????????????.....

                    timeTimerFragment.rl.setVisibility(View.INVISIBLE);
                    timeTimerFragment.progressBar.setVisibility(View.INVISIBLE);
                    workShopActivity.getIvBnvBlur().setVisibility(View.INVISIBLE);


                    int addHour = (timeThread.time)*60;
                    int addMin = timeThread.min;
                    Log.i("addHourMinTest", addHour  + ":" + addMin );


                    String jsonStr = response.body();
                    Log.i("timeSumTest", jsonStr);
                    try {
                        JSONArray jsonArray = new JSONArray(jsonStr);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String time = jsonObject.getString("timeSum");

                        String[] arr = time.split(":");
                        int hour = (Integer.parseInt(arr[0])*60);
                        int min = Integer.parseInt(arr[1]);
                        int total = ((addHour+hour) + (addMin+min));
                        int finalHour = total/60;
                        int finalMin = total - (finalHour*60);
                        String timeSum = String.format("%02d:%02d", finalHour, finalMin);

                        workItemTimeSumUpdate(workItemNo, timeSum);



                        Log.i("timeTestA", "ddda");
                        timeThread.stopThread();
                        timeThread.time=0;
                        timeThread.min=0;
                        timeThread.sec=0;

                        SharedPreferences pref = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("hour", timeThread.time);
                        editor.putInt("min", timeThread.min);
                        editor.putInt("sec", timeThread.sec);
                        editor.commit();

                        tvTime.setText("00:00:00");
                        timeThread = null;
                        timeTimerFragment.timeThread = null;
                        notifyDataSetChanged();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }

        public void timeDataGetDB(String workItemNO, String days){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call =retrofitService.timeDataGetDB(workItemNO, days);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String jsonStr = response.body();
                    Log.i("timeTestB", jsonStr);
                    try {
                        JSONArray jsonArray = new JSONArray(jsonStr);
                        JSONObject jsonObject =  jsonArray.getJSONObject(0);

                        String beforeTime = jsonObject.getString("time");
                        String[] s =beforeTime.split(":");
                        int hour = ((Integer.parseInt(s[0]))*60); //??????
                        int min = Integer.parseInt(s[1]); //???

                        int total = ((hour + (timeThread.time*60)) + (timeThread.min+min));
                        int finalHour = total/60;
                        int finalMin = total - (finalHour*60);

                        String time= String.format("%02d:%02d", finalHour, finalMin);
                        timeDataInserOrUpdatetDB(1, workItemNO, days, time, true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("timeJson", t.getMessage());
                }
            });
        }

        public void workItemTimeSumUpdate(String workItemNo, String timeSum){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<String> call = retrofitService.workItemTimeSumUpdate(workItemNo, timeSum);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                  Log.i("workItemTimeSumUpdate", response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("workItemTimeSumUpdate", t.getMessage());
                }
            });

        }


    }//################################################################
}
