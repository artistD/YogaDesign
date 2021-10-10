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

public class ProfileModifyActivity extends AppCompatActivity {


    private ImageView ivProfile;
    private EditText etUserNickName;
    private EditText etUserStateMsg;

    private String profileToString;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modify);

        ivProfile = findViewById(R.id.iv_profile);
        etUserNickName = findViewById(R.id.et_user_nickname);
        etUserStateMsg = findViewById(R.id.et_state_msg);

        pref = getSharedPreferences("Data", MODE_PRIVATE);
        String profileUriToString = pref.getString("ProfileUritoString", "");
        String profileName = pref.getString("ProfileName", "");
        String userStateMsg = pref.getString("UserStateMsg", "");

        Glide.with(this).load(Uri.parse(profileUriToString)).into(ivProfile);
        etUserNickName.setText(profileName);
        etUserStateMsg.setText(userStateMsg);
    }

    public void clickClose(View view) {
        finish();
    }

    public void clickSave(View view) {
        String profileName = etUserNickName.getText().toString();
        String userStateMsg = etUserStateMsg.getText().toString();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ProfileUritoString", profileToString);
        editor.putString("ProfileName", profileName);
        editor.putString("UserStateMsg", userStateMsg);
        editor.commit();
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
            if (result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();
                Uri uri = intent.getData();
                profileToString = uri.toString();
                Glide.with(ProfileModifyActivity.this).load(uri).into(ivProfile);
            }

        }
    });
}