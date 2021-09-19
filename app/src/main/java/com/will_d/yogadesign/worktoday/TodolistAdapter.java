package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

public class TodolistAdapter extends RecyclerView.Adapter<TodolistAdapter.VH> {
    Context context;
    ArrayList<TodolistItem> todolistItems;


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


    }

    @Override
    public int getItemCount() {
        return todolistItems.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private TextView nickName;

        private CardView cdTimeSet;
        private TextView tvTimeSet;

        private ImageView checked1;
        private ImageView checked2;
        private ImageView checked3;

        private TextView name;
        private CardView cdLog;

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


        }
    }
}
