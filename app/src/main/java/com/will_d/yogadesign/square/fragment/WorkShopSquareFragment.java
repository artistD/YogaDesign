package com.will_d.yogadesign.square.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
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

    //****************************
    private String checkdeIdentifyId ="";
    private boolean isFirst = false;
    private boolean isMemberBackgroundFirst=false;
    //****************************

    private RelativeLayout rlMemberChatting;



    //칼렌다를 제어하기 위한 친구임
    private RelativeLayout calendarDialog;
    private CalendarView calendarView;
    private RelativeLayout calendarBlur;



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

        Log.i("favoriteCheckedId", id);



        llFriendSearch = view.findViewById(R.id.ll_friend_search);
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



        recyclerViewMember = view.findViewById(R.id.recycler_member);
        squareMemberAdapter2 = new SquareMemberAdapter2();
        recyclerViewMember.setAdapter(squareMemberAdapter2);



        recyclerViewMemberItem = view.findViewById(R.id.recycler_item);
        squareMemberListAdapter = new SquareMemberListAdapter(getActivity(), squareMemberItemListItems, calendarDialog, calendarView, calendarBlur);
        recyclerViewMemberItem.setAdapter(squareMemberListAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        squareMemberLoadDB();

    }

    //아이디로 구분해버리면 될거같은데
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
            squareMemberLoadDB();
        }


    }

    public  void squareMemberFavoriteCounterUpdateDB(String favoriteCheckedId, boolean isFavorite, String favoriteCheckedUserList, int finalPosition){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.squareMemberFavoriteCounterUpdateDB(favoriteCheckedId, isFavorite, favoriteCheckedUserList);
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
               //todo:이거 교수님한테 질문해야할거같음...... 해결이 안됨 : ui 갱신이 안되는것으로 보임!!!!! 아답터의 함문제를 80%의심
               squareMemberAdapter2.notifyItemChanged(finalPosition);
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
                Log.i("sqareMemverListLoadDB", response.body());
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
                Log.i("sqareMemverListLoadDB", t.getMessage());
            }
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
                Log.i("squareMemberLoadDB", response.body());
                try {
                    JSONArray  jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String nickName = jsonObject.getString("name");
                        String imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/member/" +jsonObject.getString("frofile");

                        String id = jsonObject.getString("id");
                        String prefId = pref.getString("id", "");

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
                        //todo: 여기서 캐스트 예외가 발생하는데 이유를 모르겠넹
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
                            if (isUserPublic){
                                squareMemberItems.add(0, new SquareMemberItem(id, imgUrl, nickName, userMsg, favoriteNum, favoriteCheckedUserList));
                                squareMemberAdapter2.notifyItemChanged(0);
                                Log.i("atherMerberId", id);
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
                                sqareMemverListLoadDB(item.getId());
                                Glide.with(getActivity()).load(item.getImgUrl()).into(civFrofile);
                                tvMemberName.setText(item.getMemberName());
                                tvMemverMessage.setText(item.getStateMsg());
                                tvFavoriteMemberCount.setText(item.getFavoriteNum()+"");



                                favoriteCheckedUserList = myFavoriteCheckedUserList;

                                if (favoriteCheckedUserList.size() ==0){
                                    isFavorite = false;
                                    Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                                }else {

                                    for (int j=0; j<favoriteCheckedUserList.size(); j++){
                                        if (favoriteCheckedUserList.get(j).equals(favoriteCheckedId)){
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
                        isFirst=false;
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


            if (selectedPosition==finalPosition){
                holder.cdMemberBg.setVisibility(View.VISIBLE);
                holder.ivMemberBg.setBackgroundColor(0xFF9999FF);
                sqareMemverListLoadDB(squareMemberItem.getId());


                Glide.with(getActivity()).load(squareMemberItem.getImgUrl()).into(civFrofile);
                tvMemberName.setText(squareMemberItem.getMemberName());
                tvMemverMessage.setText(squareMemberItem.getStateMsg());
                tvFavoriteMemberCount.setText(squareMemberItem.favoriteNum + "");



                favoriteCheckedId = squareMemberItem.getId();
                favoriteCheckedUserList = squareMemberItem.getFavoriteCheckedUserList();


                if (squareMemberItem.getFavoriteCheckedUserList().size() ==0){
                    isFavorite = false;
                    Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);
                }else {

                    for (int j=0; j<squareMemberItem.getFavoriteCheckedUserList().size(); j++){
                        if (squareMemberItem.getFavoriteCheckedUserList().get(j).equals(favoriteCheckedId)){
                            isFavorite = true;
                            Glide.with(getActivity()).load(R.drawable.ic_baseline_favorite_24).into(ivMemberFavorite);
                            break;

                        }else {
                            isFavorite = false;
                            Glide.with(getActivity()).load(R.drawable.ic_empty_favorite).into(ivMemberFavorite);

                        }
                    }
                }




                Log.i("favoriteCheckedId", squareMemberItem.getId());
            }else {
                holder.cdMemberBg.setVisibility(View.INVISIBLE);
            }

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
                    isFavorite=!isFavorite;
                    String jsonStr;
                    if (isFavorite){
                        favoriteCheckedUserList.add(favoriteCheckedId);
                        Gson gson = new Gson();
                        jsonStr = gson.toJson(favoriteCheckedUserList);
                    }else {
                        favoriteCheckedUserList.remove(favoriteCheckedId);
                        Gson gson = new Gson();
                        jsonStr = gson.toJson(favoriteCheckedUserList);
                    }
                    squareMemberFavoriteCounterUpdateDB(favoriteCheckedId, isFavorite, jsonStr, finalPosition);

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

            public VH(@NonNull View itemView) {
                super(itemView);
                cdMemberBg = itemView.findViewById(R.id.cd_member_bg);
                ivMemberBg= itemView.findViewById(R.id.iv_member_bg);
                civFrofile = itemView.findViewById(R.id.civ_frofile);
                tvMemberName = itemView.findViewById(R.id.tv_member_name);
            }




        }//#########################################
    }//class SquareMemberAdapter2


}
