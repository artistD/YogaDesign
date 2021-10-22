package com.will_d.yogadesign.set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.mainActivity.LoginActivity;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import soup.neumorphism.NeumorphImageView;

public class WorkShopUserSetFragment extends Fragment {
    private NeumorphImageView btn;
    private CircleImageView civUserSettingProfile;
    private TextView tvUserSettingNickName;
    private TextView tvUserSettingStateMsg;
    private NeumorphImageView nivUserSettingModify;
    private Switch swPrivateMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_userset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        civUserSettingProfile = view.findViewById(R.id.civ_usersetting_profile);
        tvUserSettingNickName = view.findViewById(R.id.tv_usersetting_nickname);
        tvUserSettingStateMsg = view.findViewById(R.id.tv_usersetting_state_msg);
        nivUserSettingModify = view.findViewById(R.id.niv_usersetting_modify);
        btn = view.findViewById(R.id.btn);
        swPrivateMode = view.findViewById(R.id.sw_private_mode);



        //로그아웃
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                String myId = pref.getString("id","");
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();
                memberLoginStateUpdateDB(myId);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        nivUserSettingModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileModifyActivity.class));
            }
        });


        swPrivateMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isUserPrivateMode", isChecked);
                editor.commit();
                userSetPrivateModeUpdateDB(!isChecked);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        if (Global.isReLogin){
            swPrivateMode.setChecked(Global.myIsUserPrivate);
        }else {
            boolean isUserPrivateMode = pref.getBoolean("isUserPrivateMode", false);
            swPrivateMode.setChecked(isUserPrivateMode);
        }

        Glide.with(getActivity()).load(Global.myRealImgUrl).into(civUserSettingProfile);
        tvUserSettingNickName.setText(Global.myNickName);

        if (Global.myStateMsg.equals("") || Global.myStateMsg.equals("null")){
            tvUserSettingStateMsg.setText("상태메세지를 입력" + "\n" + "해주세요!");
        }else {
            tvUserSettingStateMsg.setText(Global.myStateMsg);
        }

    }

    public  void memberLoginStateUpdateDB(String myId) {
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.memberLoginStateUpdateDB(myId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               Log.i("memberLoginStateUpdateDB", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("memberLoginStateUpdateDB", t.getMessage());
            }
        });
    }


    public void userSetPrivateModeUpdateDB(boolean isPublic){
        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id","");

        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.userSetPrivateModeUpdateDB(id, isPublic);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("isPrivate", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("isPrivate", t.getMessage());
            }
        });

    }

}
