package com.will_d.yogadesign.mainActivity;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivBrand;
    private boolean isLogin = false;
    private boolean isFirstProfileSet = false;

    private ArrayList<String> idEqual = new ArrayList<>();
    private boolean isIdEqual = false;

    private SharedPreferences pref;

    private String myNickName;
    private String myProfileUrl;
    private String mySteteMsg;
    private boolean myIsUserPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();


        ivBrand = findViewById(R.id.iv_brand);
        Glide.with(this).load(R.drawable.ic_yodadesign_brand_foreground).into(ivBrand);

        pref = getSharedPreferences("Data", MODE_PRIVATE);
        isLogin = pref.getBoolean("isLogin", false);
        isFirstProfileSet = pref.getBoolean("isFirstProfileSet", false);


        if (isLogin){
            if (isFirstProfileSet){
                startActivity(new Intent(LoginActivity.this, WorkShopActivity.class));
                finish();
            }else {
                Intent intent = new Intent(LoginActivity.this, ProfileSetActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void clickKaKaoLogin(View view) {
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken!=null){
                    Toast.makeText(LoginActivity.this, "????????? ??????", Toast.LENGTH_SHORT).show();
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user!=null){
                                memberLoadDB(user);
                            }
                            return null;
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "????????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        });

    }

    public void memberLoadDB(User user){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.memberLoadDataFromServer();

        String userId = String.valueOf(user.getId()); //**********

        idEqual.clear();
        Log.i("hjyuuinbwewef", userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               String jsonStr = response.body();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        idEqual.add(id);

                        if (id.equals(userId)){

                            String nickName = jsonObject.getString("name");
                            String profileDstName = jsonObject.getString("frofile");
                            String imgUrl ="";
                            if (profileDstName.contains("k.kakao")){
                                imgUrl = profileDstName;
                            }else {
                                imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/member/" + profileDstName;
                            }
                            String stateMsg = jsonObject.getString("stateMsg");
                            String userPublicStr = jsonObject.getString("isUserPublic");
                            boolean isUserPublic = false;
                            if (userPublicStr.equals("1")){
                                isUserPublic = true;
                            }else {
                                isUserPublic = false;
                            }
                            myNickName = nickName;
                            myProfileUrl = imgUrl;
                            mySteteMsg = stateMsg;
                            myIsUserPublic = isUserPublic;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i=0;i<idEqual.size(); i++){
                    if(idEqual.get(i).equals(userId)){
                        isIdEqual = true;
                        break;
                    }else {
                        isIdEqual =false;
                        Log.i("checekdd", idEqual.get(i));
                    }
                }

                if (isIdEqual){
                    Global.myNickName = myNickName;
                    Global.myStateMsg = mySteteMsg;
                    Global.myRealImgUrl = myProfileUrl;
                    Global.myIsUserPrivate = !myIsUserPublic;
                    Global.isReLogin = true;

                    //??????????????? ??????
                    isLogin =true;
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLogin", isLogin);
                    editor.putBoolean("isFirstProfileSet", true);
                    editor.putString("id", userId);
                    editor.putBoolean("isFirstCompair", false);
                    editor.putString("myNickName", Global.myNickName);
                    editor.putString("myImgRealPathUrl", Global.myRealImgUrl);
                    editor.putString("myStateMsg", Global.myStateMsg);
                    editor.putBoolean("isReLogin", Global.isReLogin);
                    editor.commit();
                    memberLoginStateUpdateDB(true, userId);
                    return;
                }else {
                    Global.myRealImgUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    Global.myNickName = user.getKakaoAccount().getProfile().getNickname();

                    //??????????????? ??????
                    isLogin =true;
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id", userId);
                    editor.putBoolean("isLogin", isLogin);
                    editor.putBoolean("isFirstCompair", true);
                    editor.putString("myNickName", Global.myNickName);
                    editor.putString("myImgRealPathUrl", Global.myRealImgUrl);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, ProfileSetActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    public  void memberLoginStateUpdateDB(boolean isLogin, String myId) {
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.memberLoginStateUpdateDB(isLogin, myId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("memberLoginStateUpdateDB", response.body());
                startActivity(new Intent(LoginActivity.this, WorkShopActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("memberLoginStateUpdateDB", t.getMessage());
            }
        });
    }

}