package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

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
        holder.tvLog.setText(item.getLog());

    }

    @Override
    public int getItemCount() {
        return logItems.size();
    }

    public class VH extends RecyclerView.ViewHolder {//##################################################################
        private TextView tvDate;
        private TextView tvLog;


        public VH(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvLog = itemView.findViewById(R.id.tv_log);
        }
    }//##################################################################
}
