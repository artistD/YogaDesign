package com.will_d.yogadesign.mainActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.ForcedTerminationService;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    private final int PERMISSION_EX_PHOTO = 100;

    private EditText etId;
    private EditText etName;
    private ImageView ivProfile;
    private String imgpath;
    private boolean isLogin = false;
    ArrayList<String> datas = new ArrayList<>();

    private boolean isSorationFirst = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        ivProfile = findViewById(R.id.iv_profile);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        isLogin = pref.getBoolean("isLogin", false);



        //퍼미션 작업 수행
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, PERMISSION_EX_PHOTO);
        }

        memberLoadDb();

        Log.i("Login", isLogin+"");
        if(isLogin) {
            startActivity(new Intent(this, WorkShopActivity.class));
            finish();
        }

    }

    public void clickLogin(View view) {

        for (int i=0; i<datas.size(); i++){
            if(!datas.get(i).equals(etId.getText().toString())){
                continue;
            }else {
                isLogin=true;
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", isLogin);
                editor.putString("id", datas.get(i));
                editor.putBoolean("isFirstCompair", true);
                editor.commit();

                Log.i("Global", datas.get(i));
                Intent intent = new Intent(this, WorkShopActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                break;
            }
        }


    }

    public void clickFrofile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launcher.launch(intent);
    }


    public void clickDBLoad(View view) { //**암시로 사용할 버튼
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        boolean isUserPublic = true;

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
        dataPart.put("isUserPublic", String.valueOf(isUserPublic));

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


    void memberLoadDb(){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.memberLoadDataFromServer();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                Log.i("TAG", jsonStr);
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    datas.clear();
                    for (int i =0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                       String no = jsonObject.getString("no");
                       String id = jsonObject.getString("id");
                       String name = jsonObject.getString("name");
                       String a = jsonObject.getString("isUserPublic");
                       boolean isUserPublic = Boolean.parseBoolean(a);

                       String profile = jsonObject.getString("frofile");
                       String date = jsonObject.getString("date");

                        Log.i("TAG", no);
                        Log.i("TAG", id);
                        Log.i("TAG", name);
                        Log.i("TAG", isUserPublic+"");
                        Log.i("TAG", profile);
                        Log.i("TAG", date);

                        datas.add(id);

                        Log.i("Datas", datas.get(i));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAG", "Error : " + t.getMessage());
            }
        });


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



    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() ==RESULT_OK){
                Intent intent = result.getData();
                Uri uri =intent.getData();

                if (uri != null){
                    Glide.with(LoginActivity.this).load(uri).into(ivProfile);
                    imgpath = getRealPathFromUri(uri);

                }
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