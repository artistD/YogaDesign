package com.will_d.yogadesign.set;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileModifyActivity extends AppCompatActivity {


    private ImageView ivProfile;
    private EditText etUserNickName;
    private EditText etUserStateMsg;

    private boolean isPhotoChecked =false;



 ///ㅂㅈㅇ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modify);

        ivProfile = findViewById(R.id.iv_profile);
        etUserNickName = findViewById(R.id.et_user_nickname);
        etUserStateMsg = findViewById(R.id.et_state_msg);



    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load(Global.myRealImgUrl).into(ivProfile);
        etUserNickName.setText(Global.myNickName);
        etUserStateMsg.setText(Global.myStateMsg);

    }

    public void clickClose(View view) {
        finish();
    }

    public void clickSave(View view) {
        Global.myNickName = etUserNickName.getText().toString();
        Global.myStateMsg = etUserStateMsg.getText().toString();

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("myNickName", Global.myNickName);
        editor.putString("myStateMsg", Global.myStateMsg);
        editor.putString("myImgRealPathUrl", Global.myRealImgUrl);
        editor.commit();

        Global.isPrifileChanged = true;
        String id =pref.getString("id","");

        Log.i("isPhotoChecked", isPhotoChecked+"");
        memberProfileUpdateDB(id, Global.myNickName, Global.myStateMsg, isPhotoChecked);
        finish();
    }

    public void memberProfileUpdateDB(String id, String name, String userStateMsg, boolean isPhotoChecked){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        MultipartBody.Part filePart = null;
        if (isPhotoChecked){
            File file = new File(Global.myRealImgUrl);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }

        Map<String, String> dataPart = new HashMap<>();
        dataPart.put("id", id);
        dataPart.put("name", name);
        dataPart.put("userStateMsg", userStateMsg);
        dataPart.put("isPhotoChecked", String.valueOf(isPhotoChecked));

        Call<String> call = retrofitService.memberProfileUpdateDB(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("ehrherherehrerh", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("memberProfileUpdateDB", t.getMessage());
            }
        });
    }

    public void clickChangeProfile(View view) {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        boolean isPermisssion = pref.getBoolean("isPermisssion", false);
        if (isPermisssion){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            launcher.launch(intent);
        }else {
            Toast.makeText(this, "이미지 업로드 불가", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();
                Uri uri = intent.getData();
                Global.myRealImgUrl = getRealPathFromUri(uri);
                isPhotoChecked = true;
            }else {
                isPhotoChecked = false;

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