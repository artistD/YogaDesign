package com.will_d.yogadesign;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import soup.neumorphism.NeumorphCardView;

public class LoginActivity extends AppCompatActivity {

    private final int PERMISSION_EX_PHOTO = 100;

    private EditText etId;
    private EditText etName;
    private ImageView ivProfile;

    private String imgpath;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        ivProfile = findViewById(R.id.iv_profile);

        //퍼미션 작업 수행
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, PERMISSION_EX_PHOTO);
        }

    }

    public void clickLogin(View view) {

        if (isLogin){
            startActivity(new Intent(this, WorkShopActivity.class));

        }else{
            Toast.makeText(LoginActivity.this, "일치하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void clickFrofile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            if (uri != null){
                Glide.with(this).load(uri).into(ivProfile);
                imgpath = getRealPathFromUri(uri);

            }
        }
    }

    public void clickDBLoad(View view) { //**암시로 사용할 버튼
        String id = etId.getText().toString();
        String name = etName.getText().toString();

        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        MultipartBody.Part filePart = null;
        if (imgpath!=null){
            File file = new File(imgpath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);

        }

        Map<String, String> dataPart = new HashMap<>();
        dataPart.put("id", id);
        dataPart.put("name", name);

        Call<String> call = retrofitService.memberPostDataToServer(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s =  response.body();
                Toast.makeText(LoginActivity.this, "s", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_EX_PHOTO && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부버장소 접근 허용", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "이미지 업로드 불가", Toast.LENGTH_SHORT).show();
        }
    }

}