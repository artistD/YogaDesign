package com.will_d.yogadesign.square.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.square.item.MessageItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends BaseAdapter {

    private Context context;
    ArrayList<MessageItem> items;

    public ChattingAdapter(Context context, ArrayList<MessageItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences pref = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        MessageItem item = items.get(position);

        View itemView = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (item.getId().equals(id)){
            itemView = inflater.inflate(R.layout.listview_chatting_my_mesage, parent, false);
        }else {
            itemView = inflater.inflate(R.layout.listview_chatting_other_message, parent, false);
        }

        CircleImageView civProfile = itemView.findViewById(R.id.civ_profile);
        TextView tvNickName = itemView.findViewById(R.id.tv_nickname);
        TextView tvTime = itemView.findViewById(R.id.tv_time);
        ImageView ivMessageBg = itemView.findViewById(R.id.iv_message_bg);
        TextView tvMessage = itemView.findViewById(R.id.tv_message);


        Glide.with(context).load(item.getProfileUrl()).into(civProfile);
        tvNickName.setText(item.getName());
        tvTime.setText(item.getTime());

        Glide.with(context).load(R.drawable.mainbg_03).override(200,200).into(ivMessageBg);
        tvMessage.setText(item.getMessage());

        return itemView;
    }
}
