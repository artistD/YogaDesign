package com.will_d.yogadesign.square;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.will_d.yogadesign.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkShopSquareFragment extends Fragment {

    private LinearLayout llFriendSearch;


    //리사이클러뷰는 한텀으로 끝내장
    private RecyclerView recyclerViewMember;
    private ArrayList<SquareMemberItem> squareMemberItems = new ArrayList<SquareMemberItem>();
    private SquareMemberAdapter squareMemberAdapter;

    private CircleImageView civFrofile;
    private TextView tvMemberName;
    private TextView tvMemverMessage;

    private ImageView ivMemberFavorite;
    private TextView tvMemberCount;


    //리사이클러뷰는 한텀으로 끝내장
    private RecyclerView recyclerViewMemberItem;
    private ArrayList<SquareMemberItemListItem> squareMemberItemListItems = new ArrayList<SquareMemberItemListItem>();
    private SquareMemberListAdapter squareMemberListAdapter;

    private RelativeLayout rlMemberChatting;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_square, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llFriendSearch = view.findViewById(R.id.ll_friend_search);
        civFrofile = view.findViewById(R.id.civ_frofile);
        tvMemberName = view.findViewById(R.id.tv_member_name);
        tvMemverMessage = view.findViewById(R.id.tv_member_message);

        ivMemberFavorite = view.findViewById(R.id.iv_member_favorite);
        tvMemberCount = view.findViewById(R.id.tv_member_favorite_count);

        rlMemberChatting = view.findViewById(R.id.rl_member_chatting);


        String imgUrl = "data:image/jpeg;base64,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";
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));

        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));
        squareMemberItems.add(new SquareMemberItem(imgUrl, "messi"));




        recyclerViewMember = view.findViewById(R.id.recycler_member);
        squareMemberAdapter = new SquareMemberAdapter(getActivity(), squareMemberItems);
        recyclerViewMember.setAdapter(squareMemberAdapter);



        String imgUrl2 = "http://img2.sbs.co.kr/img/seditor/VD/2020/04/09/VD62139889_w640.jpg";

        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));
        squareMemberItemListItems.add(new SquareMemberItemListItem(imgUrl2, "morning", "매일아침 운동하기", 42, 32));



        recyclerViewMemberItem = view.findViewById(R.id.recycler_item);
        squareMemberListAdapter = new SquareMemberListAdapter(getActivity(), squareMemberItemListItems);
        recyclerViewMemberItem.setAdapter(squareMemberListAdapter);






    }
}
