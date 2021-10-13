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





//    private InputMethodManager imm;

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

//        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


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



    }

    public void clickBack(View view) {
//        imm.hideSoftInputFromWindow(etChatting.getWindowToken(),0);
//        intent = getIntent();
//        intent.putExtra("isFirst", true);
//        setResult(RESULT_OK, intent);
        finish();

    }


}