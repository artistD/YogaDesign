package com.will_d.yogadesign.mainActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileSetActivity extends AppCompatActivity {
    private final int PERMISSION_EX_PHOTO = 1018;

    private ImageView ivProfile;
    private EditText etUserNickName;
    private EditText etUserStateMsg;
    private RelativeLayout rlProfileSetBlur;

    private boolean isPhotoChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);

        ivProfile = findViewById(R.id.iv_profile);
        etUserNickName = findViewById(R.id.et_user_nickname);
        etUserStateMsg = findViewById(R.id.et_state_msg);
        rlProfileSetBlur = findViewById(R.id.rl_profileset_blur);

        Glide.with(this).load(Global.myRealImgUrl).into(ivProfile);
        etUserNickName.setText(Global.myNickName);
        etUserStateMsg.setText("");

        //퍼미션 작업 수행
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, PERMISSION_EX_PHOTO);
        }
    }

    public void clickClose(View view) {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id","");
        editor.putBoolean("isFirstCompair", false);
        editor.putBoolean("isLogin", false);
        editor.putBoolean("isFirstProfileChecked", false);
        editor.putString("myNickName", "");
        editor.putString("myImgRealPathUrl", "");
        editor.commit();
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

        rlProfileSetBlur.setVisibility(View.VISIBLE);
        meberInsertDB();
    }

    public void meberInsertDB(){
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        String id = pref.getString("id","");
        boolean isLogin = pref.getBoolean("isLogin", false);
        String nickName = etUserNickName.getText().toString();
        String stateMsg = etUserStateMsg.getText().toString();

        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Map<String, String> dataPart = new HashMap<>();

        MultipartBody.Part filePart = null;
        if (isPhotoChecked){
            File file = new File(Global.myRealImgUrl);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }else {
            dataPart.put("myKaKaoHttpStr", Global.myRealImgUrl);
        }
        dataPart.put("isPhotoChecked", String.valueOf(isPhotoChecked)); //************** 이게 제일 중요한 판단일걱 같은데??
        dataPart.put("id", id);
        dataPart.put("nickName", nickName);
        dataPart.put("stateMsg", stateMsg);
        dataPart.put("isLogin", String.valueOf(isLogin));
        dataPart.put("isUserPublic", String.valueOf(true));
        dataPart.put("favoriteNum", String.valueOf(0));
        ArrayList<String> arr = new ArrayList<>();
        Gson gson = new Gson();
        String favoriteCheckedUserList = gson.toJson(arr);
        dataPart.put("favoriteCheckedUserList", favoriteCheckedUserList);

        Call<String> call = retrofitService.meberInsertDB(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                rlProfileSetBlur.setVisibility(View.INVISIBLE);
                Log.i("memberPostDataToServer", response.body());
                startActivity(new Intent(ProfileSetActivity.this, WorkShopActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("memberPostDataToServer", t.getMessage());
            }
        });



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
                isPhotoChecked = true;
            }else {
                isPhotoChecked = false;
            }
        }
    });
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_EX_PHOTO && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부버장소 접근 허용", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "이미지 업로드 불가", Toast.LENGTH_SHORT).show();
        }
    }



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