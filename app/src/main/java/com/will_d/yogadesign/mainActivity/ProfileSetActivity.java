package com.will_d.yogadesign.mainActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class ProfileSetActivity extends AppCompatActivity {

    ImageView ivProfile;
    EditText etUserNickName;
    EditText etUserStateMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);

        ivProfile = findViewById(R.id.iv_profile);
        etUserNickName = findViewById(R.id.et_user_nickname);
        etUserStateMsg = findViewById(R.id.et_state_msg);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        String uriToString = pref.getString("ProfileUritoString", "");
        String userNickName = pref.getString("ProfileName", "");

        Glide.with(this).load(Uri.parse(uriToString)).into(ivProfile);
        etUserNickName.setText(userNickName);


    }

    public void clickClose(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void clickStart(View view) {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isFirstProfileChecked", true);
        editor.putString("ProfileName",etUserNickName.getText().toString());
        editor.putString("UserStateMsg", etUserStateMsg.getText().toString());
        editor.commit();
        startActivity(new Intent(this, WorkShopActivity.class));
        finish();
    }


    public void clickChangeProfile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launcher.launch(intent);
    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                Uri uri = intent.getData();
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ProfileUritoString", uri.toString());
                editor.commit();
                Glide.with(ProfileSetActivity.this).load(uri).into(ivProfile);
            }
        }
    });
}