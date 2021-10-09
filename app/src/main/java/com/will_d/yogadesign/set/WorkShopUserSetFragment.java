package com.will_d.yogadesign.set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.mainActivity.LoginActivity;
import com.will_d.yogadesign.R;

import de.hdodenhof.circleimageview.CircleImageView;
import soup.neumorphism.NeumorphImageView;

public class WorkShopUserSetFragment extends Fragment {
    private NeumorphImageView btn;
    private CircleImageView civUserSettingProfile;
    private TextView tvUserSettingNickName;
    private TextView tvUserSettingStateMsg;
    private NeumorphImageView nivUserSettingModify;



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

        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String profileUritoString = pref.getString("ProfileUritoString", "");
        Log.i("ProfileUri", profileUritoString);
        String nickName = pref.getString("ProfileName", "");
        String userStateMsg = pref.getString("UserStateMsg", "");

        Glide.with(getActivity()).load(Uri.parse(profileUritoString)).into(civUserSettingProfile);
        tvUserSettingNickName.setText(nickName);
        tvUserSettingStateMsg.setText(userStateMsg);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();



                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        nivUserSettingModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==getActivity().RESULT_OK){

            }
        }
    });


}
