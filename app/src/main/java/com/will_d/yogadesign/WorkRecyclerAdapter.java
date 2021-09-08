package com.will_d.yogadesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkRecyclerAdapter extends RecyclerView.Adapter<WorkRecyclerAdapter.VH> {

    private Context context;
    private ArrayList<WorkItem> items;

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
        WorkItem item = items.get(position);

        String imgUrl = item.getImgUrl();
        Glide.with(context).load(imgUrl).into(holder.civ);
        holder.tvProperty.setText(item.getProperty());
        holder.ivGaol.setBackgroundColor(item.getGoalColor());
        holder.ivPreNotification.setBackgroundColor(item.getPreNotificationColor());
        holder.ivLocationNotification.setBackgroundColor(item.getLocationNotificationColor());

        holder.tvExplanation.setText(item.getExplanation());
        holder.tvAddexplanation.setText(item.getAddExplanation());

        int[] weeksColor = item.getWeeksColor();
        for (int i=0; i<weeksColor.length; i++){
            holder.weeks[i].setTextColor(weeksColor[i]);
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private CircleImageView civ;
        private TextView tvProperty;
        private ImageView ivGaol;
        private ImageView ivPreNotification;
        private ImageView ivLocationNotification;
        private TextView tvExplanation;
        private TextView tvAddexplanation;

        private TextView[] weeks = new TextView[7];


        public VH(@NonNull View itemView) {
            super(itemView);

            civ = itemView.findViewById(R.id.civ);
            tvProperty = itemView.findViewById(R.id.tv_property);
            ivGaol = itemView.findViewById(R.id.iv_goal);
            ivPreNotification = itemView.findViewById(R.id.iv_preNotification);
            ivLocationNotification = itemView.findViewById(R.id.iv_locationNotification);

            tvExplanation = itemView.findViewById(R.id.tv_explanation);
            tvAddexplanation = itemView.findViewById(R.id.tv_addexplanation);

            weeks[0] = itemView.findViewById(R.id.tv_week_mon);
            weeks[1] = itemView.findViewById(R.id.tv_week_tues);
            weeks[2] = itemView.findViewById(R.id.tv_week_wendnes);
            weeks[3] = itemView.findViewById(R.id.tv_week_thurs);
            weeks[4] = itemView.findViewById(R.id.tv_week_fri);
            weeks[5] = itemView.findViewById(R.id.tv_week_satur);
            weeks[6] = itemView.findViewById(R.id.tv_week_sun);


        }
    }
}
