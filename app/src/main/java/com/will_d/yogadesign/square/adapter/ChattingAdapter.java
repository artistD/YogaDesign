package com.will_d.yogadesign.square.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.square.item.MessageItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import soup.neumorphism.NeumorphCardView;

public class ChattingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MessageItem> items;
    private RelativeLayout rlChattingDeleteDialog;
    private TextView tvChattingDeleteOk;
    private TextView tvChattingDeleteCancel;

    public ChattingAdapter(Context context, ArrayList<MessageItem> items, RelativeLayout rlChattingDeleteDialog, TextView tvChattingDeleteOk, TextView tvChattingDeleteCancel) {
        this.context = context;
        this.items = items;
        this.rlChattingDeleteDialog = rlChattingDeleteDialog;
        this.tvChattingDeleteOk = tvChattingDeleteOk;
        this.tvChattingDeleteCancel  = tvChattingDeleteCancel;
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
        TextView tvMessage = itemView.findViewById(R.id.tv_message);
        NeumorphCardView ncd = itemView.findViewById(R.id.ncd);


        Glide.with(context).load(item.getProfileUrl()).into(civProfile);
        tvNickName.setText(item.getName());
        tvTime.setText(item.getTime());

        tvMessage.setText(item.getMessage());

        if (item.getId().equals(id)){
            ncd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    rlChattingDeleteDialog.setVisibility(View.VISIBLE);

                    tvChattingDeleteCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rlChattingDeleteDialog.setVisibility(View.INVISIBLE);
                        }
                    });

                    tvChattingDeleteOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.remove(position);
                            memberMyMessageDeleteDB(item.getNo());
                            notifyDataSetChanged();
                            rlChattingDeleteDialog.setVisibility(View.INVISIBLE);
                        }
                    });



                    return true;
                }
            });
        }




        return itemView;
    }

    public void memberMyMessageDeleteDB(String no){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call =retrofitService.memberMyMessageDeleteDB(no);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("memberMyMessageDeleteDB", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("memberMyMessageDeleteDB", t.getMessage());
            }
        });

    }
}
