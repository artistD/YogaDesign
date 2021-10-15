package com.will_d.yogadesign.square.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.square.adapter.ChattingAdapter;
import com.will_d.yogadesign.square.item.MessageItem;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {

    private TextView userName;
    private EditText etChatting;

    private ArrayList<MessageItem> items = new ArrayList<>();
    private ListView listView;
    private ChattingAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        userName = findViewById(R.id.tv_username);
        etChatting = findViewById(R.id.et_chatting);

        //todo: Intent를 여러마리 보내면 구분은 어떻게 하지????
        Intent intent = getIntent();
        String checkedId = intent.getStringExtra("checkedId");
        String userNickName = intent.getStringExtra("userNickName");

        userName.setText(userNickName);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        String id = pref.getString("id","");
        String imgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c1/Lionel_Messi_20180626.jpg";
        String imgUrl2 = "http://image.newsis.com/2021/04/14/NISI20210414_0017347131_web.jpg";

        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));



        listView = findViewById(R.id.listview);
        adapter = new ChattingAdapter(this, items);
        listView.setAdapter(adapter);


        //진행방향이 어떻게 진행되어야 하냐면
        //일단 자신의 정보를 DB에 저장할수 있어야함
        //DB테이블 설계 forignkey!!
        // - 자신의 [id, 이름, 날짜, 사진, message] ,, 이 상대방의 id : 이것을 통해 읽어들이도록 할거임
        // 저장한 데이터를 불러들어와야함 클릭한 상대방의 id를 통해서!!!
        // 자신이 글을 쓴것은 자신이 삭제할수 있도록 해야함

        //일단 첫번째로 자신의 개인정보를 등록해야하는데 이것을 쉐어드 프리퍼런스로 해가주고 지금 복작하게 동작이 잘안되는데
        //일단 이거부터 손봐야겠음 그다음 1~4번을 끝낸다
        //그리고 다양한 테스트 진행  - 카카오로그인 구글로그인 하면 일단 최종단계는 끝
        //그다음 timer 기능과 알람메니져를 이용해서 위치, 시간에따른 알람 서비스를 구현할수 있도록 하면 진짜끝 얼마 안남음 일단 주말동안 다끝내는것을 못표로함



    }

    public void clickBack(View view) {
        finish();

    }


}