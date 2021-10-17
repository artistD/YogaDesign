package com.will_d.yogadesign.square.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.square.item.SquareMemberItemListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import soup.neumorphism.NeumorphImageView;

public class SquareMemberListAdapter extends RecyclerView.Adapter<SquareMemberListAdapter.VH> {

    private Context context;
    private ArrayList<SquareMemberItemListItem> squareMemberItemListItems;
    private RelativeLayout calendarDialog;
    private CalendarView calendarView;
    private RelativeLayout calrendarBlur;

    public SquareMemberListAdapter(Context context, ArrayList<SquareMemberItemListItem> squareMemberItemListItems, RelativeLayout calendarDialog, CalendarView calendarView, RelativeLayout calrendarBlur) {
        this.context = context;
        this.squareMemberItemListItems = squareMemberItemListItems;
        this.calendarDialog = calendarDialog;
        this.calendarView = calendarView;
        this.calrendarBlur  = calrendarBlur;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_square_member_list, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SquareMemberItemListItem item = squareMemberItemListItems.get(position);
        Glide.with(context).load(item.getImgUrl()).into(holder.civMemberListPhoto);

        holder.tvMemberListNickName.setText(item.getNickName());
        holder.tvMemberListName.setText(item.getName());
        holder.tvMemberListCounter.setText(item.getCounter()+"");
        holder.tvMemberListTimeSum.setText(item.getTimeSum());

    }

    @Override
    public int getItemCount() {
        return squareMemberItemListItems.size();
    }

    public  class VH extends RecyclerView.ViewHolder{
        private CircleImageView civMemberListPhoto;
        private TextView tvMemberListNickName;
        private TextView tvMemberListName;

        private TextView tvMemberListCounter;
        private TextView tvMemberListTimeSum;

        private NeumorphImageView nivWeeks;
        private ArrayList<Calendar> calendars = new ArrayList<>();

        public VH(@NonNull View itemView) {
            super(itemView);

            civMemberListPhoto = itemView.findViewById(R.id.civ_memberlist_photo);
            tvMemberListNickName = itemView.findViewById(R.id.tv_memberlist_nickname);
            tvMemberListName = itemView.findViewById(R.id.tv_memberlist_name);

            tvMemberListCounter = itemView.findViewById(R.id.tv_memberlist_counter);
            tvMemberListTimeSum = itemView.findViewById(R.id.tv_memberlist_timesum);


            nivWeeks = itemView.findViewById(R.id.niv_weeks);

            nivWeeks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SquareMemberItemListItem item = squareMemberItemListItems.get(getAdapterPosition());
                    calendarDataLoadFromServer(item.getNo());
                    calendarDialog.setVisibility(View.VISIBLE);

                }
            });

            calrendarBlur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendarDialog.setVisibility(View.INVISIBLE);
                }
            });





        }

        public void calendarDataLoadFromServer(String workItemNo){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.calendarDataLoadFromServer(workItemNo);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String jsonStr = response.body();
                    calendars.clear();

                    try {
                        JSONArray jsonArray  = new JSONArray(jsonStr);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String days = jsonObject.getString("days");
                            Log.i("Tagcalendar", days);
                            String[] daysArr = days.split(":");
                            int year = Integer.parseInt(daysArr[0]);
                            int month = Integer.parseInt(daysArr[1]);
                            int day = Integer.parseInt(daysArr[2]);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, (month-1), day);
                            calendars.add(calendar);
                        }

                        calendarView.setSelectedDates(calendars);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("Tagcalendar", t.getMessage());
                }
            });
        }





    }
}
