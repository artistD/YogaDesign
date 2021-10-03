package com.will_d.yogadesign.time.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.time.fragment.TimeTimerFragment;
import com.will_d.yogadesign.time.item.TimerItem;
import com.will_d.yogadesign.worktoday.item.WorkItem;

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
    private boolean isLogModify = false;
    private boolean isTimeFirst = false;

    private TimeTimerFragment.TimeThread timeThread;


    public TimerAdapter(Context context, ArrayList<TimerItem> items) {
        this.context = context;
        this.items = items;
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
        for (int i=0; i<Global.workItems.size(); i++){
            WorkItem workItem = Global.workItems.get(i);
            if (workItem.getNo().equals(item.getWorkItemNo())){
                isLogModify = workItem.getIsLogModify();
                isTimeFirst = workItem.getIsTimeFirst();
                break;
            }else {
                continue;
            }


        }


        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = sdf.format(date);

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        String dayStr = "";

        switch (day_of_week){
            case 1: //일
                dayStr = "일";
                break;

            case 2: //일
                dayStr = "월";
                break;

            case 3: //일
                dayStr = "화";
                break;

            case 4: //일
                dayStr = "수";
                break;

            case 5: //일
                dayStr = "목";
                break;

            case 6: //일
                dayStr = "금";
                break;

            case 7: //일
                dayStr = "토";
                break;
        }
        String days = getTime + ".(" + dayStr + ")";



        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeThread !=null){

                    if (isLogModify){   // 수정을했다는것임
                        if (isTimeFirst){
                            //이떄는 추가해주는 코드를 써야
                            String time = timeThread.time + ":" + timeThread.min;
                            holder.timeDataUpdateDB(item.getWorkItemNo(), days, time, true);
                        }else {
                            String time = timeThread.time + ":" + timeThread.min;
                            holder.timeDataUpdateDB(item.getWorkItemNo(), days, time, true);
                        }

                    }else { //수정을 안했다는 뜻이고
                        if (isTimeFirst) { //true면 처음이 아니라는 뜻
                            //기존에 있던것을 불러와서 더해줘야함
                            //근데 이건그냥 업데이트로 가자! 다음에 업로드해주고 그냥 해주자 하하하ㅏ
                            //어떻게 하냐면 기존에있던 코드를 가져오면됨
                            String time = timeThread.time + ":" + timeThread.min;
                            holder.timeDataInsertDB(1, item.getWorkItemNo(), days, time, true);
                        }else {
                            String time = timeThread.time + ":" + timeThread.min;
                            holder.timeDataInsertDB(0, item.getWorkItemNo(), days, time, true);
                        }
                    }

                }else {
                    Toast.makeText(context, "저장할수 있는 시간이 없습니다!", Toast.LENGTH_SHORT).show();
                }






            }
        });

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
        //0이면 추가하는거고
        //1이면 업데이트하는것임
        public void timeDataInsertDB(int identifier, String workItemNo, String day, String time, boolean isTimeFirst){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.timeDataInsertDB(identifier, workItemNo, day, time, isTimeFirst);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });



        }
        public void timeDataUpdateDB(String workItemNo, String day, String time, boolean isTimeFirst){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.timeDataUpdateDB(workItemNo, day, time, isTimeFirst);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }//################################################################
}
