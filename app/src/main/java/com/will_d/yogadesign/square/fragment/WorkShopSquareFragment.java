package com.will_d.yogadesign.square.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.Circle;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.square.activity.ChattingActivity;
import com.will_d.yogadesign.square.adapter.SquareMemberAdapter;
import com.will_d.yogadesign.square.adapter.SquareMemberListAdapter;
import com.will_d.yogadesign.square.item.SquareMemberItem;
import com.will_d.yogadesign.square.item.SquareMemberItemListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorkShopSquareFragment extends Fragment {

    private LinearLayout llFriendSearch;


    //리사이클러뷰는 한텀으로 끝내장
    private RecyclerView recyclerViewMember;
    private ArrayList<SquareMemberItem> squareMemberItems = new ArrayList<SquareMemberItem>();
    private SquareMemberAdapter2 squareMemberAdapter2;
    private ProgressBar pgMember;
    private LinearLayout llStateBlur;

    private String myId;
    private String myImgUrl;
    private String myNickName;
    private String myUserStateMsg;
    private int myFavoriteNum;
    private ArrayList<String> myFavoriteCheckedUserList;



    private CircleImageView civFrofile;
    private TextView tvMemberName;
    private TextView tvMemverMessage;

    private ImageView ivMemberFavorite;
    private boolean isFavorite=false;
    private String favoriteCheckedId="";
    private TextView tvFavoriteMemberCount;
    private ArrayList<String> favoriteCheckedUserList;


    //리사이클러뷰는 한텀으로 끝내장
    private RecyclerView recyclerViewMemberItem;
    private ArrayList<SquareMemberItemListItem> squareMemberItemListItems = new ArrayList<SquareMemberItemListItem>();
    private SquareMemberListAdapter squareMemberListAdapter;
    private ProgressBar pgMemberList;

    //****************************
    private String checkdeIdentifyId ="";
//    public static boolean isFirst = false;
    private boolean isMemberBackgroundFirst=false;
    //****************************

    private RelativeLayout rlMemberChatting;



    //칼렌다를 제어하기 위한 친구임
    private RelativeLayout calendarDialog;
    private CalendarView calendarView;
    private RelativeLayout calendarBlur;


    //chatting을 할때 파라미터를 전달해야함 그들을 위한 데이터들이 필요함
    private String userName;


    private  boolean isFirst = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_square, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isFirst = true;
        isMemberBackgroundFirst = true;
        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id","");
        checkdeIdentifyId = id;
        favoriteCheckedId = id;
        userName = Global.myNickName;

        Log.i("favoriteCheckedId", id);


        civFrofile = view.findViewById(R.id.civ_frofile);
        tvMemberName = view.findViewById(R.id.tv_member_name);
        tvMemverMessage = view.findViewById(R.id.tv_member_message);

        ivMemberFavorite = view.findViewById(R.id.iv_member_favorite);
        tvFavoriteMemberCount = view.findViewById(R.id.tv_member_favorite_count);

        rlMemberChatting = view.findViewById(R.id.rl_member_chatting);

        //칼렌다를 제어하기 위찬 친구임
        calendarDialog = view.findViewById(R.id.rl_calendar_dialog);
        calendarView = view.findViewById(R.id.calendar);
        calendarBlur = view.findViewById(R.id.calendarBlur);



        pgMember = view.findViewById(R.id.progress_member);
        llStateBlur = view.findViewById(R.id.ll_state_blur);
        recyclerViewMember = view.findViewById(R.id.recycler_member);
        squareMemberAdapter2 = new SquareMemberAdapter2();
        recyclerViewMember.setAdapter(squareMemberAdapter2);



        pgMemberList = view.findViewById(R.id.progress_memberlist);
        recyclerViewMemberItem = view.findViewById(R.id.recycler_item);
        squareMemberListAdapter = new SquareMemberListAdapter(getActivity(), squareMemberItemListItems, calendarDialog, calendarView, calendarBlur);
        recyclerViewMemberItem.setAdapter(squareMemberListAdapter);


        rlMemberChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                intent.putExtra("checkedId", favoriteCheckedId);
                intent.putExtra("userNickName", userName);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst){
            memberLoading();
            memberListLoading();
            squareMemberLoadDB();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
            if (Global.isPrifileChanged){

               squareMemberItems.remove(0);
               squareMemberItems.add(0, new SquareMemberItem(myId, Global.myRealImgUrl, Global.myNickName, Global.myStateMsg, myFavoriteNum, myFavoriteCheckedUserList));
               squareMemberAdapter2.notifyItemChanged(0);
               squareMemberAdapter2.notifyItemMoved(0,0);

               Glide.with(getActivity()).load(Global.myRealImgUrl).into(civFrofile);
               tvMemberName.setText(Global.myNickName);
               tvMemverMessage.setText(Global.myStateMsg);
               Global.isPrifileChanged = false;
            }
        }

    }

    public void memberLoading(){

            pgMember.setVisibility(View.VISIBLE);
            llStateBlur.setVisibility(View.INVISIBLE);
            recyclerViewMember.setVisibility(View.INVISIBLE);

    }

    public void memberListLoading(){

            pgMemberList.setVisibility(View.VISIBLE);
            recyclerViewMemberItem.setVisibility(View.INVISIBLE);

    }

    public  void squareMemberFavoriteCounterUpdateDB(String myId, String favoriteCheckedId, boolean isFavorite, String favoriteCheckedUserList, int finalPosition){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.squareMemberFavoriteCounterUpdateDB(myId, favoriteCheckedId, isFavorite, favoriteCheckedUserList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String favoriteNum = response.body();
                Log.i("squareMemberFavoriteCounterUpdateDB", response.body());
               if (isFavorite){
                   Glide.with(getActivity()).load(R.drawable.ic_baseline_favorite_24).into(ivMemberFavorite);
                   tvFavoriteMemberCount.setText(favoriteNum);
               }else {
                   Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                   tvFavoriteMemberCount.setText(favoriteNum);
               }

               SquareMemberItem squareMemberItem = squareMemberItems.get(finalPosition);
               squareMemberItem.favoriteNum = Integer.parseInt(favoriteNum);
               squareMemberAdapter2.notifyItemChanged(finalPosition);
                Toast.makeText(getActivity(), finalPosition+":" + squareMemberItem.favoriteNum, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("squareMemberFavoriteCounterUpdateDB", t.getMessage());
            }
        });
    }

    public void sqareMemverListLoadDB(String id){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);


        squareMemberItemListItems.clear();
        squareMemberListAdapter.notifyDataSetChanged();
        Call<String> call = retrofitService.sqareMemverListLoadDB(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pgMemberList.setVisibility(View.INVISIBLE);
                recyclerViewMemberItem.setVisibility(View.VISIBLE);
                String jsonStr = response.body();
                try {
                    JSONArray jsonArray  = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String no = jsonObject.getString("no");
                        String nickName = jsonObject.getString("nickName");
                        String name  = jsonObject.getString("name");
                        int counterNum = Integer.parseInt(jsonObject.getString("Completenum"));
                        String timeSum = jsonObject.getString("timeSum");
                        String imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/workitem/" + jsonObject.getString("dstName");

                        squareMemberItemListItems.add(0, new SquareMemberItemListItem(no, imgUrl, nickName, name, counterNum, timeSum));
                        squareMemberListAdapter.notifyItemChanged(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("sqareMemverListLoadDB", t.getMessage()); }
        });
    }

    public void squareMemberLoadDB(){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.squareMemberLoadDB();

        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);

        squareMemberItems.clear();
        squareMemberAdapter2.notifyDataSetChanged();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                Log.i("qwghrtjyuuilofgd", response.body());
                llStateBlur.setVisibility(View.VISIBLE);
                pgMember.setVisibility(View.INVISIBLE);
                recyclerViewMember.setVisibility(View.VISIBLE);
                try {
                    JSONArray  jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String nickName = jsonObject.getString("name");
                        String imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/member/" +jsonObject.getString("frofile");

                        String id = jsonObject.getString("id");
                        String prefId = pref.getString("id", "");

                        String ss = jsonObject.getString("isLogin");
                        boolean isLogin =false;
                        if (ss.equals("1")){
                            isLogin = true;
                        }else {
                            isLogin = false;
                        }

                        String s = jsonObject.getString("isUserPublic");
                        boolean isUserPublic =false;
                        if (s.equals("1")){
                            isUserPublic = true;
                        }else {
                            isUserPublic = false;
                        }

                        String userMsg = jsonObject.getString("stateMsg");
                        if(userMsg.equals("null")){
                            userMsg = "아직 상태메세지가 없어요!";
                        }

                        String fav = jsonObject.getString("favoriteNum");
                        Log.i("favoriteNum", fav);
                        int favoriteNum = Integer.parseInt(fav);

                        String arr = jsonObject.getString("favoriteCheckedUserList");
                        Log.i("aaarrrr", arr);
                        Gson gson = new Gson();
                        String[] myChekedfavoriteUserArr = gson.fromJson(arr, String[].class);
                        ArrayList<String> favoriteCheckedUserList= new ArrayList<String>(Arrays.asList(myChekedfavoriteUserArr));


                        if (id.equals(prefId)){
                            myId = id;
                            myImgUrl = imgUrl;
                            myNickName = nickName;
                            myUserStateMsg = userMsg;
                            myFavoriteNum = favoriteNum;
                            myFavoriteCheckedUserList = favoriteCheckedUserList;
                            Log.i("myMemberId", myId);
                        }else {
                            if(isLogin){

                                if (isUserPublic){
                                    squareMemberItems.add(0, new SquareMemberItem(id, imgUrl, nickName, userMsg, favoriteNum, favoriteCheckedUserList));
                                    squareMemberAdapter2.notifyItemChanged(0);
                                    Log.i("atherMerberId", id);
                                }

                            }

                        }



                    }

                    squareMemberItems.add(0, new SquareMemberItem(myId, myImgUrl, myNickName, myUserStateMsg, myFavoriteNum, myFavoriteCheckedUserList));
                    squareMemberAdapter2.notifyItemChanged(0);

                    if (isFirst){
                        //todo: statemsg 작업이 끝나면 여기서 부터 작업을 해야함
                        for (int i=0; i<squareMemberItems.size();i++){
                            SquareMemberItem item = squareMemberItems.get(i);
                            if (item.getId().equals(checkdeIdentifyId)){
                                memberListLoading();
                                sqareMemverListLoadDB(item.getId());
                                Glide.with(getActivity()).load(item.getImgUrl()).into(civFrofile);
                                tvMemberName.setText(item.getMemberName());
                                tvMemverMessage.setText(item.getStateMsg());
                                tvFavoriteMemberCount.setText(item.getFavoriteNum()+"");




                                if (myFavoriteCheckedUserList.size() ==0){
                                    isFavorite = false;
                                    Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                                }else {

                                    for (int j=0; j<myFavoriteCheckedUserList.size(); j++){
                                        if (myFavoriteCheckedUserList.get(j).equals(favoriteCheckedId)){
                                            isFavorite = true;
                                            Glide.with(getActivity()).load(R.drawable.ic_baseline_favorite_24).into(ivMemberFavorite);
                                            break;
                                        }else {
                                            isFavorite = false;
                                            Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                                        }
                                    }

                                }



                            }else {
                                continue;
                            }

                        }
                        isFirst = false;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("squareMemberLoadDB", t.getMessage());
            }
        });

    }

    public class SquareMemberAdapter2 extends RecyclerView.Adapter<SquareMemberAdapter2.VH> {
        private int oldPosition = -1;
        private int selectedPosition = -1;

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_square_member, parent, false);
            return new VH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            final int finalPosition = position;
            SquareMemberItem squareMemberItem = squareMemberItems.get(finalPosition);
            Glide.with(getActivity()).load(squareMemberItem.getImgUrl()).into(holder.civFrofile);
            holder.tvMemberName.setText(squareMemberItem.getMemberName());


            //todo:이거 교수님한테 질문해야함 !!!!
            for (int i=0; i<myFavoriteCheckedUserList.size(); i++){
                String myFav = myFavoriteCheckedUserList.get(i);
                if (myFav.equals(squareMemberItem.getId())){
                    holder.ivMemberFavoriteState.setVisibility(View.VISIBLE);

                }else {
                    holder.ivMemberFavoriteState.setVisibility(View.INVISIBLE);

                }
            }

            if (selectedPosition==finalPosition){
                holder.cdMemberBg.setVisibility(View.VISIBLE);
                holder.ivMemberBg.setBackgroundColor(0xFF9999FF);
                Log.i("ChattingActivityData", finalPosition+"");
                memberListLoading();
                sqareMemverListLoadDB(squareMemberItem.getId());


                Glide.with(getActivity()).load(squareMemberItem.getImgUrl()).into(civFrofile);

                Glide.with(getActivity()).load(squareMemberItem.getImgUrl()).into(civFrofile);
                tvMemberName.setText(squareMemberItem.getMemberName());
                tvMemverMessage.setText(squareMemberItem.getStateMsg());
                holder.squareFavoiteNumLoadDB(squareMemberItem.getId());




                favoriteCheckedId = squareMemberItem.getId();
                favoriteCheckedUserList = squareMemberItem.getFavoriteCheckedUserList();
                userName = squareMemberItem.getMemberName();


                if (myFavoriteCheckedUserList.size() ==0){
                    isFavorite = false;
                    Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                }else {

                    for (int j=0; j<myFavoriteCheckedUserList.size(); j++){
                        if (myFavoriteCheckedUserList.get(j).equals(favoriteCheckedId)){
                            isFavorite = true;
                            Glide.with(getActivity()).load(R.drawable.ic_baseline_favorite_24).into(ivMemberFavorite);
                            Log.i("squareMemberItemdd", myFavoriteCheckedUserList.size()+"");
                            break;

                        }else {
                            isFavorite = false;
                            Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                            Log.i("squareMemberItemaa", myFavoriteCheckedUserList.size()+"");
                        }
                    }
                }




                Log.i("favoriteCheckedId", squareMemberItem.getId());
            }else {
                holder.cdMemberBg.setVisibility(View.INVISIBLE);
            }


            //todo:버튼클릭할떄마다 서버에서 불러들어오게 작업해야한다 숫자를
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldPosition = selectedPosition;
                    selectedPosition = finalPosition;
                    notifyDataSetChanged();

                }
            });

            if (isMemberBackgroundFirst){
                if (finalPosition==0){
                    holder.cdMemberBg.setVisibility(View.VISIBLE);
                    Log.i("BindMemberId", squareMemberItem.getId());
                    holder.ivMemberBg.setBackgroundColor(0xFF9999FF);
                    isMemberBackgroundFirst = false;
                }
            }




            ivMemberFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                    String myId = pref.getString("id","");

                    isFavorite=!isFavorite;
                    String jsonStr;
                    if (isFavorite){
                        myFavoriteCheckedUserList.add(favoriteCheckedId);
                        Gson gson = new Gson();
                        jsonStr = gson.toJson(myFavoriteCheckedUserList);
                    }else {
                        myFavoriteCheckedUserList.remove(favoriteCheckedId);
                        Gson gson = new Gson();
                        jsonStr = gson.toJson(myFavoriteCheckedUserList);
                    }
                    squareMemberFavoriteCounterUpdateDB(myId, favoriteCheckedId, isFavorite, jsonStr, finalPosition);

                }
            });











        }

        @Override
        public int getItemCount() {
            return squareMemberItems.size();
        }

        class VH extends RecyclerView.ViewHolder {//#########################################
            private ImageView ivMemberBg;
            private CardView cdMemberBg;
            private CircleImageView civFrofile;
            private TextView tvMemberName;
            private ImageView ivMemberFavoriteState;
            public VH(@NonNull View itemView) {
                super(itemView);
                cdMemberBg = itemView.findViewById(R.id.cd_member_bg);
                ivMemberBg= itemView.findViewById(R.id.iv_member_bg);
                civFrofile = itemView.findViewById(R.id.civ_frofile);
                tvMemberName = itemView.findViewById(R.id.tv_member_name);
                ivMemberFavoriteState = itemView.findViewById(R.id.iv_member_favorite_state);
            }

            public void squareFavoiteNumLoadDB(String id){
                Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                Call<String> call = retrofitService.squareFavoiteNumLoadDB(id);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String favoriteNum = response.body();
                        tvFavoriteMemberCount.setText(String.valueOf(favoriteNum));

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }



        }//#########################################
    }//class SquareMemberAdapter2


}
