package com.will_d.yogadesign.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.will_d.yogadesign.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class TestActivity extends AppCompatActivity {

    ImageView ivProfile;
    TextView tvNickName;
    TextView tvId;


    String imgUrl;
    String nickName;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ivProfile = findViewById(R.id.iv_profiletest);
        tvNickName = findViewById(R.id.tv_nickNametest);
        tvId = findViewById(R.id.tv_idtest);


    }

    public void clickBtn(View view) {
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken!=null){
                    Toast.makeText(TestActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user!=null){

                                id = user.getId();
                                nickName = user.getKakaoAccount().getProfile().getNickname();
                                imgUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();

                                tvNickName.setText(nickName);
                                tvId.setText(id+"");
                                Glide.with(TestActivity.this).load(imgUrl).into(ivProfile);
                                Log.i("qwdwefggerqj", imgUrl);
                            }
                            return null;
                        }
                    });
                }else {
                    Toast.makeText(TestActivity.this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        });
    }
}