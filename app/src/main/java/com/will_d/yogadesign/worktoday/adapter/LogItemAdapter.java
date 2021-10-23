

package com.will_d.yogadesign.worktoday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.worktoday.item.LogItem;

import java.util.ArrayList;

public class LogItemAdapter extends RecyclerView.Adapter<LogItemAdapter.VH> {

    Context context;
    ArrayList<LogItem> logItems;


    public LogItemAdapter(Context context, ArrayList<LogItem> logItems) {
        this.context = context;
        this.logItems = logItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_logitem, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogItemAdapter.VH holder, int position) {
        LogItem item = logItems.get(position);

        holder.tvDate.setText(item.getDate());


        if (item.getLog().equals("null")){
            holder.tvLog.setVisibility(View.GONE);
        }else {
            holder.tvLog.setVisibility(View.VISIBLE);
            holder.tvLog.setText(item.getLog());
        }

        if (item.getTime().equals("null")){
            holder.cdWorkItemTime.setVisibility(View.INVISIBLE);
        }else {
            holder.cdWorkItemTime.setVisibility(View.VISIBLE);
            holder.tvWorkItemTime.setText(item.getTime());
        }


        Glide.with(context).load(R.drawable.mainbg_03).into(holder.iv);


    }

    @Override
    public int getItemCount() {
        return logItems.size();
    }

    public class VH extends RecyclerView.ViewHolder {//##################################################################
        private TextView tvDate;
        private TextView tvLog;

        private ImageView iv;

        private CardView cdWorkItemTime;
        private TextView tvWorkItemTime;


        public VH(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvLog = itemView.findViewById(R.id.tv_log);

            iv= itemView.findViewById(R.id.iv);

            cdWorkItemTime = itemView.findViewById(R.id.cd_workitem_time);
            tvWorkItemTime = itemView.findViewById(R.id.tv_workitem_time);
        }
    }//##################################################################
}
