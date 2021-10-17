package com.will_d.yogadesign.mainActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;


public class ProfileSetActivity extends AppCompatActivity {

    private ImageView ivProfile;
    private EditText etUserNickName;
    private EditText etUserStateMsg;

    //넣어줘야함
    private String uriToString;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);

        ivProfile = findViewById(R.id.iv_profile);
        etUserNickName = findViewById(R.id.et_user_nickname);
        etUserStateMsg = findViewById(R.id.et_state_msg);

//        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
//        String uriToString = pref.getString("ProfileUritoString", "");
//        String userNickName = pref.getString("ProfileName", "");
//
//        Glide.with(this).load(Uri.parse(uriToString)).into(ivProfile);
//        etUserNickName.setText(userNickName);


    }

    public void clickClose(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void clickStart(View view) {
        Global.myNickName = etUserNickName.getText().toString();
        Global.myStateMsg = etUserStateMsg.getText().toString();

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isFirstProfileChecked", true);
        editor.putString("myNickName", Global.myNickName);
        editor.putString("myStateMsg", Global.myStateMsg);
        editor.putString("myImgRealPathUrl", Global.myRealImgUrl);
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
                Global.myRealImgUrl = getRealPathFromUri(uri);

                Glide.with(ProfileSetActivity.this).load(uri).into(ivProfile);
            }
        }
    });


    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }
}