package com.will_d.yogadesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class WorkShopWorkFragment extends Fragment {

    ArrayList<WorkItem> workItems = new ArrayList<>();
    RecyclerView recyclerView;
    WorkRecyclerAdapter adapter;

    NeumorphCardView cdAddBtn;
    NeumorphCardView cdAddBtn2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_work, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjCjgVv-4Cl9Z-XQT3uCV_KKtjPzSNG-q2XA&usqp=CAU";
        int[] weekColor = new int[]{0xFF999999,0xFF999999,0xFF333333,0xFF999999,0xFF333333,0xFF999999,0xFF333333};
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));
        workItems.add(new WorkItem(imgUrl, "passion", 0xFF999999, 0xFF999999, 0xFF9999FF, "dwqdqwqwdqwd", "dwqdwqfqwqfw", weekColor));



        recyclerView =view.findViewById(R.id.recycler);
        adapter = new WorkRecyclerAdapter(getActivity(), workItems);
        recyclerView.setAdapter(adapter);

        cdAddBtn = view.findViewById(R.id.cd_addbtn);
        cdAddBtn2 = view.findViewById(R.id.cd_addbtn2);
        cdAddBtn.setBackgroundColor(0xFFC7DDFF);
        cdAddBtn2.setBackgroundColor(0xFFC7DDFF);

    }
}
