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
import com.google.gson.Gson;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.squareup.picasso.Picasso;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.ForcedTerminationService;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
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

    private Uri uri;
    private String imgpath;
    private boolean isLogin = false;
    private boolean isFirstProfileChecked = false;

    private ArrayList<String> datas = new ArrayList<>();

    //*************************************************
    private ArrayList<String> favoriteCheckedUserList = new ArrayList<>();
    //*************************************************

    private ImageView ivBrand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        ivProfile = findViewById(R.id.iv_profile);

        ivBrand = findViewById(R.id.iv_brand);
        Glide.with(this).load(R.drawable.ic_yodadesign_brand_foreground).into(ivBrand);

        //퍼미션 작업 수행
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, PERMISSION_EX_PHOTO);
        }

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        isLogin = pref.getBoolean("isLogin", false);
        isFirstProfileChecked = pref.getBoolean("isFirstProfileChecked", true);


        if (!isFirstProfileChecked){
            Intent intent = new Intent(this, ProfileSetActivity.class);
            startActivity(intent);
            finish();
        }else if(isLogin && isFirstProfileChecked) {
            startActivity(new Intent(this, WorkShopActivity.class));
            finish();
        }




    }

    public void clickKaKaoLogin(View view) {
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken!=null){
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user!=null){
                                Global.myRealImgUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();
                                Global.myNickName = user.getKakaoAccount().getProfile().getNickname();
                                String id = String.valueOf(user.getId());
                                SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);


                                isLogin =true;
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id", id);
                                editor.putBoolean("isLogin", isLogin);
                                editor.putBoolean("isFirstCompair", true);
                                editor.commit();

                                isFirstProfileChecked = pref.getBoolean("isFirstProfileChecked", false);
                                Log.i("Global", id);
                                if (isFirstProfileChecked){
                                    Intent intent = new Intent(LoginActivity.this, WorkShopActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }else {
                                    Intent intent = new Intent(LoginActivity.this, ProfileSetActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }

                            }
                            return null;
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show();
                }
                return null;
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


    public void clickLogin(View view) {

        for (int i=0; i<datas.size(); i++){
            if(!datas.get(i).equals(etId.getText().toString())){
                continue;
            }else {
                isLogin=true;
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", isLogin);
                editor.putBoolean("isFirstCompair", true);
                editor.putString("id", datas.get(i));
                editor.commit();

                isFirstProfileChecked = pref.getBoolean("isFirstProfileChecked", false);
                Log.i("Global", datas.get(i));
                if (isFirstProfileChecked){
                    Intent intent = new Intent(this, WorkShopActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else {
                    Intent intent = new Intent(this, ProfileSetActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

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
        dataPart.put("favoriteNum", String.valueOf(0));
        dataPart.put("isUserPublic", String.valueOf(isUserPublic));

        Gson gson = new Gson();
        String jsonStr = gson.toJson(favoriteCheckedUserList);
        dataPart.put("favoriteCheckedUserList", jsonStr);

        Call<String> call = retrofitService.meberInsertDB(dataPart, filePart);
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

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() ==RESULT_OK){
                Intent intent = result.getData();
                uri =intent.getData();

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