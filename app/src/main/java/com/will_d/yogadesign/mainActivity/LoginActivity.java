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
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivBrand;
    private boolean isLogin = false;
    private boolean isFirstProfileChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivBrand = findViewById(R.id.iv_brand);
        Glide.with(this).load(R.drawable.ic_yodadesign_brand_foreground).into(ivBrand);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        isLogin = pref.getBoolean("isLogin", false);
        Log.i("isLogin", isLogin +"");
        isFirstProfileChecked = pref.getBoolean("isFirstProfileChecked", false);
        Log.i("isFirstProfileChecked", isFirstProfileChecked + "");

        if(isLogin && !isFirstProfileChecked){
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
                                if (isFirstProfileChecked){

                                }else {
                                    Global.myRealImgUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();
                                    Global.myNickName = user.getKakaoAccount().getProfile().getNickname();
                                }

                                String id = String.valueOf(user.getId());
                                SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);
                                if (isFirstProfileChecked){
                                    isLogin =true;
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("isLogin", isLogin);
                                }else {
                                    isLogin =true;
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("id", id);
                                    editor.putBoolean("isLogin", isLogin);
                                    editor.putBoolean("isFirstCompair", true);
                                    editor.putString("myNickName", Global.myNickName);
                                    editor.putString("myImgRealPathUrl", Global.myRealImgUrl);
                                    editor.commit();
                                }


//                                isFirstProfileChecked = pref.getBoolean("isFirstProfileChecked", false);
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

}