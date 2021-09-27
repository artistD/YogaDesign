package com.will_d.yogadesign.square;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SquareMemberListAdapter extends RecyclerView.Adapter<SquareMemberListAdapter.VH> {

    Context context;
    ArrayList<SquareMemberItemListItem> squareMemberItemListItems;

    public SquareMemberListAdapter(Context context, ArrayList<SquareMemberItemListItem> squareMemberItemListItems) {
        this.context = context;
        this.squareMemberItemListItems = squareMemberItemListItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

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

        public VH(@NonNull View itemView) {
            super(itemView);

            civMemberListPhoto = itemView.findViewById(R.id.civ_memberlist_photo);
            tvMemberListNickName = itemView.findViewById(R.id.tv_memberlist_nickname);
            tvMemberListName = itemView.findViewById(R.id.tv_memberlist_name);

//            tvMemberListCounter = itemView.findViewById(R.id)
        }
    }
}
