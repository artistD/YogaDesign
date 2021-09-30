package com.will_d.yogadesign.worktoday.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.WorkShopActivity;
import com.will_d.yogadesign.worktoday.GworkToday;
import com.will_d.yogadesign.worktoday.ItemTouchHelperListener;
import com.will_d.yogadesign.worktoday.RetrofitHelper;
import com.will_d.yogadesign.worktoday.RetrofitService;
import com.will_d.yogadesign.worktoday.activity.WokrDataSetActivity;
import com.will_d.yogadesign.worktoday.activity.WorkItemClickedActivity;
import com.will_d.yogadesign.worktoday.item.WorkItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkRecyclerAdapter extends RecyclerView.Adapter<WorkRecyclerAdapter.VH> implements ItemTouchHelperListener {

    private Context context;
    private ArrayList<WorkItem> items;

    private String no;

    //아이템 퍼블릭의 불린값임
    boolean popupIsItemPublick = true;

    public WorkRecyclerAdapter(Context context, ArrayList<WorkItem> items) {
        this.context = context;
        this.items = items;
    }




    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_workitem2, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final int filnalPosition = position;
        WorkItem item = items.get(position);

        String imgUrl = item.getImgUrl();
        Glide.with(context).load(imgUrl).into(holder.civ);
        holder.tvNickname.setText(item.getNickName());
        holder.tvName.setText(item.getName());

        if (item.getIsGoal()) {
            holder.ivGaol.setBackgroundColor(0xFF9999FF);
            holder.tvGoal.setVisibility(View.VISIBLE);
            holder.tvGoal.setText(item.getTvgoal());
        }
        else {
            holder.ivGaol.setBackgroundColor(0xFFeeeeee);
        }

        if (item.getIspreNotification()) {
            holder.ivPreNotification.setBackgroundColor(0xFF9999FF);
            holder.tvPreNotification.setVisibility(View.VISIBLE);
            holder.tvPreNotification.setText(item.getTvpreNotification());
        }
        else {
            holder.ivPreNotification.setBackgroundColor(0xFFeeeeee);
        }

        if (item.getIslocationNotification()) {
            holder.ivLocationNotification.setBackgroundColor(0xFF9999FF);
            holder.tvLocalNotification.setVisibility(View.VISIBLE);
            holder.tvLocalNotification.setText(item.getTvlocalNotification());
        }
        else {
            holder.ivLocationNotification.setBackgroundColor(0xFFeeeeee);
        }


        boolean[] weeks = item.getIsweeks();
        for (int i=0; i<weeks.length; i++){
            if (weeks[i]) holder.weeks[i].setTextColor(0xFF9999FF);
            else holder.weeks[i].setTextColor(0xFF999999);
        }

        Log.i("TEST", imgUrl + item.getNickName() + item.getIsGoal() + item.getIspreNotification() + item.getIslocationNotification() + item.getName());

        if (item.getIsItemInOff()){
            holder.sw.setChecked(true);
            holder.llItenContainer.setBackgroundResource(R.drawable.mainbg_03);
        }else {
            holder.sw.setChecked(false);
            holder.llItenContainer.setBackgroundColor(0x33333333);
        }

        holder.tvWorkItemCounter.setText(item.getCompleteNum()+"");

        boolean[] isDayOrTodaySelected = item.getIsDayOrTodaySelected();


        if (!isDayOrTodaySelected[0] && isDayOrTodaySelected[1]){
            holder.cdTodayWork.setVisibility(View.VISIBLE);
        }


        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                WorkItem item = items.get(filnalPosition);
                Log.i("TAG", filnalPosition + "");
                Log.i("asdf", item.getNo() + isChecked);
                if (isChecked) {
                    holder.llItenContainer.setBackgroundResource(R.drawable.mainbg_03);
                } else {
                    holder.llItenContainer.setBackgroundColor(0x33333333);
                }

                holder.WorkitemSwitchOnOffLoadDB(isChecked, item.getNo());
            }
        });


        holder.ivItemSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow =  holder.showPopup(filnalPosition);
                Toast.makeText(context, "qwfqwfqwf", Toast.LENGTH_SHORT).show();
                no = item.getNo();

            }
        });//#######



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        WorkItem workItem_from= items.get(from_position);
        WorkItem workItem_to= items.get(to_position);
        Log.i("Tagposition", from_position +  " : " + to_position + "");
        items.remove(from_position);
        items.add(to_position, workItem_from);
        notifyItemMoved(from_position,to_position);
        workItemChangePositionInsertToDB(workItem_from.getNo(), workItem_from.getSortationNo(), workItem_to.getNo(), workItem_to.getSortationNo());
        return false;
    }



    @Override
    public void onItemSwipe(int position) {
            WorkItem workItem = items.get(position);
            items.remove(position);
            notifyItemRemoved(position);
            items.add(workItem);
            notifyItemInserted(items.size()-1);

    }


    public class VH extends RecyclerView.ViewHolder{

        private CircleImageView civ;
        private TextView tvNickname;
        private TextView tvName;

        private ImageView ivGaol;
        private TextView tvGoal;
        private ImageView ivPreNotification;
        private TextView tvPreNotification;
        private ImageView ivLocationNotification;
        private TextView tvLocalNotification;

        private TextView[] weeks = new TextView[7];


        private Switch sw;
        private LinearLayout llItenContainer;
        private ImageView ivItemSet;


        private TextView tvWorkItemCounter;

        private CardView cdTodayWork;


        public Switch getSw() {//####################getter
            return sw;
        }

        public VH(@NonNull View itemView) {//#################################################################
            super(itemView);
            civ = itemView.findViewById(R.id.civ);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvName = itemView.findViewById(R.id.tv_name);

            ivGaol = itemView.findViewById(R.id.iv_goal);
            tvGoal = itemView.findViewById(R.id.tv_goal);
            ivPreNotification = itemView.findViewById(R.id.iv_preNotification);
            tvPreNotification = itemView.findViewById(R.id.tv_preNotification);
            ivLocationNotification = itemView.findViewById(R.id.iv_locationNotification);
            tvLocalNotification = itemView.findViewById(R.id.tv_localNotification);

            weeks[0] = itemView.findViewById(R.id.tv_week_mon);
            weeks[1] = itemView.findViewById(R.id.tv_week_tues);
            weeks[2] = itemView.findViewById(R.id.tv_week_wendnes);
            weeks[3] = itemView.findViewById(R.id.tv_week_thurs);
            weeks[4] = itemView.findViewById(R.id.tv_week_fri);
            weeks[5] = itemView.findViewById(R.id.tv_week_satur);
            weeks[6] = itemView.findViewById(R.id.tv_week_sun);


            sw = itemView.findViewById(R.id.sw);
            llItenContainer = itemView.findViewById(R.id.ll_itemcontainer);
            ivItemSet = itemView.findViewById(R.id.iv_itemSet);

            tvWorkItemCounter = itemView.findViewById(R.id.tv_workitem_counter);


            cdTodayWork = itemView.findViewById(R.id.cd_today_work);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkItem item = items.get(getAdapterPosition());
                    Toast.makeText(context, "asdf", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, WorkItemClickedActivity.class);
                    intent.putExtra("workItemNo", item.getNo());
                    intent.putExtra("name", item.getName());
                    intent.putExtra("nickName", item.getNickName());
                    context.startActivity(intent);

                }
            });



        }//#################################################################




        public PopupWindow showPopup(int finalPosition){
            LayoutInflater inflater = LayoutInflater.from(context);
            View popupView = inflater.inflate(R.layout.workitem_popupmenu, null);



            RelativeLayout popupRlModify =  popupView.findViewById(R.id.rl_modify);
            RelativeLayout popupRlDelete = popupView.findViewById(R.id.rl_delete);
            RelativeLayout popupRlPublick = popupView.findViewById(R.id.rl_itempublic);
            TextView tvItemPublic = popupView.findViewById(R.id.tv_itempublic);


            int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;

            PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setElevation(10f);

            PopupWindowCompat.showAsDropDown(popupWindow, ivItemSet, 0, 0, Gravity.CENTER);



            popupRlModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WokrDataSetActivity.class);
                    intent.putExtra("no", no);

                    Log.i("qaz", no);
                    WorkItem item= items.get(finalPosition);
                    GworkToday.no = item.getNo();
                    GworkToday.isworkitemModifyChcecked = true;
                    GworkToday.isModifySave = true;


                    context.startActivity(intent);
                    popupWindow.dismiss();

                    WorkShopActivity workShopActivity = (WorkShopActivity) context;
                    workShopActivity.overridePendingTransition(R.anim.activity_data_set, R.anim.fragment_none);
                }
            });


            //todo: 쇼우 팝업 동작안함 한번하고나면.....왜 와이
            popupRlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkItem item = items.get(finalPosition);
                    popupWindow.dismiss();
                    item.getRlWorkitemDeleteDialog().setVisibility(View.VISIBLE);
                    item.getTvWorkitemDeleteOK().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.remove(finalPosition);
                            notifyDataSetChanged();
                            workitemDeleteDB(item);
                            item.getRlWorkitemDeleteDialog().setVisibility(View.INVISIBLE);

                        }
                    });

                    item.getTvWorkitemDeleteCancel().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.getRlWorkitemDeleteDialog().setVisibility(View.INVISIBLE);

                        }
                    });
                }
            });


            popupRlPublick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupIsItemPublick = !popupIsItemPublick;
                    Toast.makeText(context, "qwfqwffqw", Toast.LENGTH_SHORT).show();
                    if (popupIsItemPublick) tvItemPublic.setText("on");
                    else tvItemPublic.setText("off");
                    WorkItem workItem = items.get(finalPosition);
                    workItemPublicUpdate(workItem.getNo(), popupIsItemPublick);
                    notifyDataSetChanged();
                }
            });


            return popupWindow;

        }


        public void WorkitemSwitchOnOffLoadDB(boolean isChecked, String no){ //사실은 인설트인데 지금 수정하기 빡셈 일단 넘겨!

            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.WorkItemSwitchOnOffGetDataDB(isChecked, no);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("asdf", response.body());
//                    GworkToday.isSwitchEnd =true;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("asdf", t.getMessage());
                }
            });

        }

        public void workitemDeleteDB(WorkItem item){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<String> call = retrofitService.workitemDeleteDB(item.getNo());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("TAG", response.body());
//                    GworkToday.isDeletEnd = true;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("TAG", t.getMessage());
                }
            });



        }

        public void workItemPublicUpdate(String no, boolean isItemPublic){
            Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
            RetrofitService retrofitServic= retrofit.create(RetrofitService.class);

            Call<String> call = retrofitServic.workItemPublicUpdate(no, isItemPublic);
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



    }

    public void workItemChangePositionInsertToDB(String fromNo, String fromSortationNo, String toNo, String toSortationNo){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.workItemChangePositionInsertToDB(fromNo, fromSortationNo, toNo, toSortationNo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("retrofitPosition", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("retrofitPosition", t.getMessage());
            }
        });





    }

}
