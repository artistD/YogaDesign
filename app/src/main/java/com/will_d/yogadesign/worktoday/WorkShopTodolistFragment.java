package com.will_d.yogadesign.worktoday;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.will_d.yogadesign.R;
import com.will_d.yogadesign.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WorkShopTodolistFragment extends Fragment {

    ArrayList<TodolistItem> todolistItems = new ArrayList<>();
    TodolistAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_todolist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));
//        todolistItems.add(new TodolistItem("ddd", "dqwdqw", false, false, false));



        adapter = new TodolistAdapter(getActivity(), todolistItems);
        recyclerView = view.findViewById(R.id.todolist_recycler);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkTodayDataServer();
    }

    void loadWorkTodayDataServer(){
        SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        Log.i("eee", id);

        String baseUrl = "http://willd88.dothome.co.kr/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call = retrofitService.TodolistLoadDataFromServer(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String jsonStr = response.body();
                Log.i("loadTodolistData", response.body());
                todolistItems.clear();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");

                        String nickName = jsonObject.getString("nickName");

                        String weeksDataJsonStr = jsonObject.getString("weeksData");
                        Gson gson = new Gson();
                        boolean[] weeksData = gson.fromJson(weeksDataJsonStr, boolean[].class);


                        String isg = jsonObject.getString("isGoalChecked");
                        boolean isGoalChecked = false;
                        if (isg.equals("1")){
                            isGoalChecked = true;
                        }else if (isg.equals("0")){
                            isGoalChecked = false;
                        }

                        String goalSet = jsonObject.getString("goalSet");

                        String cNum = jsonObject.getString("Completenum");
                        int completeNum = Integer.parseInt(cNum);



                        todolistItems.add(0,new TodolistItem(name, nickName, false, false, false));
                        adapter.notifyItemChanged(0);

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
}
