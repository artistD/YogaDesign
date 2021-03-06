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

        //todo: Intent??? ???????????? ????????? ????????? ????????? ??????????
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


        //??????????????? ????????? ??????????????? ?????????
        //?????? ????????? ????????? DB??? ???????????? ????????????
        //DB????????? ?????? forignkey!!
        // - ????????? [id, ??????, ??????, ??????, message] ,, ??? ???????????? id : ????????? ?????? ?????????????????? ?????????
        // ????????? ???????????? ????????????????????? ????????? ???????????? id??? ?????????!!!
        // ????????? ?????? ????????? ????????? ???????????? ????????? ?????????

        //?????? ???????????? ????????? ??????????????? ????????????????????? ????????? ????????? ?????????????????? ???????????? ?????? ???????????? ????????? ???????????????
        //?????? ???????????? ??????????????? ????????? 1~4?????? ?????????
        //????????? ????????? ????????? ??????  - ?????????????????? ??????????????? ?????? ?????? ??????????????? ???
        //????????? timer ????????? ?????????????????? ???????????? ??????, ??????????????? ?????? ???????????? ???????????? ????????? ?????? ????????? ?????? ????????? ?????? ???????????? ?????????????????? ????????????

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