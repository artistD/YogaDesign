package com.will_d.yogadesign;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkRecyclerAdapter extends RecyclerView.Adapter<WorkRecyclerAdapter.VH> implements ItemTouchHelperListener{

    private Context context;
    private ArrayList<WorkItem> items;

    private VH holder;

    public VH getHolder() {
        return holder;
    }

    public WorkRecyclerAdapter(Context context, ArrayList<WorkItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_workitem, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        this.holder=holder;
        WorkItem item = items.get(position);

        String imgUrl = item.getImgUrl();
        Glide.with(context).load(imgUrl).into(holder.civ);
        holder.tvNickname.setText(item.getNickName());

        if (item.getIsGoal()) holder.ivGaol.setBackgroundColor(0xFF9999FF);
        else holder.ivGaol.setBackgroundColor(0xFF999999);

        if (item.getIspreNotification()) holder.ivPreNotification.setBackgroundColor(0xFF9999FF);
        else holder.ivPreNotification.setBackgroundColor(0xFF999999);

        if (item.getIslocationNotification()) holder.ivLocationNotification.setBackgroundColor(0xFF9999FF);
        else holder.ivLocationNotification.setBackgroundColor(0xFF999999);

        holder.tvName.setText(item.getName());
        holder.tvAddexplanation.setText(item.getAddExplanation());

        boolean[] weeks = item.getIsweeks();
        for (int i=0; i<weeks.length; i++){
            if (weeks[i]) holder.weeks[i].setTextColor(0xFF9999FF);
            else holder.weeks[i].setTextColor(0xFF999999);
        }




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        WorkItem workItem = items.get(from_position);
        items.remove(from_position);
        items.add(to_position, workItem);
        notifyItemMoved(from_position,to_position);
        return true;
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
        private ImageView ivGaol;
        private ImageView ivPreNotification;
        private ImageView ivLocationNotification;
        private TextView tvName;
        private TextView tvAddexplanation;
        private TextView[] weeks = new TextView[7];


        private Switch sw;
        private LinearLayout llItenContainer;
        private ImageView ivItemSet;

        private RelativeLayout popupRlModify;
        private RelativeLayout popupRlDelete;


        public VH(@NonNull View itemView) {//#################################################################
            super(itemView);
            civ = itemView.findViewById(R.id.civ);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            ivGaol = itemView.findViewById(R.id.iv_goal);
            ivPreNotification = itemView.findViewById(R.id.iv_preNotification);
            ivLocationNotification = itemView.findViewById(R.id.iv_locationNotification);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddexplanation = itemView.findViewById(R.id.tv_addexplanation);
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


            ivItemSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup();

                    popupRlModify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Modify", Toast.LENGTH_SHORT).show();
                        }
                    });

                    popupRlDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            });//#######





            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        llItenContainer.setBackgroundResource(R.drawable.mainbg_03);
                    }
                    else {
                        llItenContainer.setBackgroundColor(0x33333333);
                    }


                }
            });

        }//#################################################################



        public void showPopup(){
            LayoutInflater inflater = LayoutInflater.from(context);
            View popupView = inflater.inflate(R.layout.workitem_popupmenu, null);

            if(popupRlModify==null) {
                popupRlModify =  popupView.findViewById(R.id.rl_modify);
                Toast.makeText(context, "qwfwfq", Toast.LENGTH_SHORT).show();
            }
            if(popupRlDelete==null){
                popupRlDelete = popupView.findViewById(R.id.rl_delete);
                Toast.makeText(context, "qwfwfq", Toast.LENGTH_SHORT).show();
            }


            int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;

            PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setElevation(10f);

            PopupWindowCompat.showAsDropDown(popupWindow, ivItemSet, 0, 0, Gravity.CENTER);

        }

        public Switch getSw() {//####################getter
            return sw;
        }
    }





}
