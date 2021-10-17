package com.will_d.yogadesign.square.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.will_d.yogadesign.R;
import com.will_d.yogadesign.service.Global;
import com.will_d.yogadesign.service.RetrofitHelper;
import com.will_d.yogadesign.service.RetrofitService;
import com.will_d.yogadesign.square.adapter.ChattingAdapter;
import com.will_d.yogadesign.square.item.MessageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import soup.neumorphism.NeumorphCardView;

public class ChattingActivity extends AppCompatActivity {

    private TextView userName;
    private EditText etChatting;
    private NeumorphCardView ncdSendMessage;

    private String checkedId;

    private ArrayList<MessageItem> items = new ArrayList<>();
    private ListView listView;
    private ChattingAdapter adapter;
    private ProgressBar pgChatting;

    private RelativeLayout rlChattingDeleteDialog;
    private TextView tvChattingDeleteOk;
    private TextView tvChattingDeleteCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        userName = findViewById(R.id.tv_username);
        etChatting = findViewById(R.id.et_chatting);
        ncdSendMessage = findViewById(R.id.ncd_send_message);

        rlChattingDeleteDialog = findViewById(R.id.rl_chatting_delete_dialog);
        tvChattingDeleteOk = findViewById(R.id.tv_chatting_delete_ok);
        tvChattingDeleteCancel = findViewById(R.id.tv_chatting_delete_cancel);

        //todo: Intent를 여러마리 보내면 구분은 어떻게 하지????
        Intent intent = getIntent();
        checkedId = intent.getStringExtra("checkedId");
        String userNickName = intent.getStringExtra("userNickName");

        Log.i("TWGa", userNickName);
        userName.setText(userNickName);

//        String id  = "willd88";
//
//        String imgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c1/Lionel_Messi_20180626.jpg";
//        String imgUrl2 = "http://image.newsis.com/2021/04/14/NISI20210414_0017347131_web.jpg";
//
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfq", "2021.10.12", imgUrl));
//        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqdwqwdqwdqwfqwfqwfqwfqwfqwdqwfqwfqwfqwfqwfqwfqwfqwfqwfqwfqwfqwfqwfqwfqwqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
//        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
//        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
//        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));
//        items.add(new MessageItem(id, "messi", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl));
//        items.add(new MessageItem("", "neymar", "dqwqwfqwfqwfqwgqwgqwgqwqwg", "2021.10.12", imgUrl2));



        listView = findViewById(R.id.listview);
        adapter = new ChattingAdapter(this, items, rlChattingDeleteDialog, tvChattingDeleteOk, tvChattingDeleteCancel);
        listView.setAdapter(adapter);
        pgChatting = findViewById(R.id.progress_chatting);



        loading();
        memberChattingMessageLoadToDB(checkedId);


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

        ncdSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);
                String id = pref.getString("id","");
                String myMsg = etChatting.getText().toString();

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy.MM.dd");
                String getTime = simpleDate.format(date);

                memberSendMessageInsertDB(id, myMsg, getTime);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
                etChatting.setText("");

            }
        });

    }

    public void loading(){
        listView.setVisibility(View.INVISIBLE);
        pgChatting.setVisibility(View.VISIBLE);
    }


    public void memberChattingMessageLoadToDB(String checkedId){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService  = retrofit.create(RetrofitService.class);


        items.clear();
        Call<String> call = retrofitService.memberChattingMessageLoadToDB(checkedId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                listView.setVisibility(View.VISIBLE);
                pgChatting.setVisibility(View.INVISIBLE);
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String no = jsonObject.getString("no");
                        String id = jsonObject.getString("id");
                        String nickName = jsonObject.getString("myNickName");
                        String dstName = jsonObject.getString("dstName");
                        String imgUrl = "http://willd88.dothome.co.kr/YogaDesign2/member/" + dstName;
                        String myMsg = jsonObject.getString("myMsg");
                        String day = jsonObject.getString("day");

                        items.add(new MessageItem(no, id, nickName, myMsg, day, imgUrl));
                        adapter.notifyDataSetChanged();
                        listView.setSelection(items.size()-1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });



    }

    public void memberSendMessageInsertDB(String id, String myMsg, String getTime){
        Retrofit retrofit = RetrofitHelper.getRetrofitScalars();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        MultipartBody.Part filePart = null;
        File file = new File(Global.myRealImgUrl);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        filePart = MultipartBody.Part.createFormData("img", file.getName(), requestBody);


        Map<String, String> dataPart = new HashMap<>();
        dataPart.put("id", id);
        dataPart.put("userCheckedId", checkedId);
        dataPart.put("myNickName", Global.myNickName);
        dataPart.put("myMsg",myMsg);
        dataPart.put("day", getTime);

        Call<String> call = retrofitService.memberSendMessageInsertDB(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("wpqkfqhrhtlvek", response.body());
                String jsonStr = response.body();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
                    String no = jsonObject.getString("no");
                    Log.i("twefgwgwegw", no);
                    items.add(new MessageItem(no, id, Global.myNickName, myMsg, getTime, Global.myRealImgUrl));
                    listView.setSelection(items.size()-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("wpqkfqhrhtlvek", t.getMessage());
            }
        });

    }

    public void clickBack(View view) {
        etChatting.setText("");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        etChatting.setText("");
        finish();
    }
}