package com.will_d.yogadesign.time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

public class AlarmItemAdapter extends RecyclerView.Adapter<AlarmItemAdapter.VH> {

    Context context;
    ArrayList<AlarmItem> alarmItems;

    public AlarmItemAdapter(Context context, ArrayList<AlarmItem> alarmItems) {
        this.context = context;
        this.alarmItems = alarmItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_alarmitem, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmItemAdapter.VH holder, int position) {
        AlarmItem item = alarmItems.get(position);
        holder.tvTime.setText(item.getTime());

        boolean[] weeks = item.getWeeks();
        for (int i=0; i<weeks.length; i++){
            if (weeks[i]) holder.weeks[i].setTextColor(0xFF9999FF);
            else holder.weeks[i].setTextColor(0xFF999999);
        }

    }

    @Override
    public int getItemCount() {
        return alarmItems.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView[] weeks = new TextView[7];

        public VH(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_time);
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
