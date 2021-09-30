package com.will_d.yogadesign.square;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SquareMemberAdapter extends RecyclerView.Adapter<SquareMemberAdapter.VH> {

    Context context;
    ArrayList<SquareMemberItem> squareMemberItems;


    public SquareMemberAdapter(Context context, ArrayList<SquareMemberItem> squareMemberItems) {
        this.context = context;
        this.squareMemberItems = squareMemberItems;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_square_member, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SquareMemberItem squareMemberItem = squareMemberItems.get(position);
        Glide.with(context).load(squareMemberItem.getImgUrl()).into(holder.civFrofile);

        holder.tvMemberName.setText(squareMemberItem.getMemberName());
    }

    @Override
    public int getItemCount() {
        return squareMemberItems.size();
    }

    public class VH extends RecyclerView.ViewHolder{//###############################################################

        private CircleImageView civFrofile;
        private TextView tvMemberName;

        public VH(@NonNull View itemView) {
            super(itemView);
            civFrofile = itemView.findViewById(R.id.civ_frofile);
            tvMemberName = itemView.findViewById(R.id.tv_member_name);


        }//###############################################################

    }
}
