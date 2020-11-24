package com.example.javayongtest.MainInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.javayongtest.R;

import java.util.ArrayList;

public class CreateRoom extends AppCompatActivity {

    Button textCode;  //방에서 코드값이 출력되는 문구
    Button gameStart;  //게임 시작 버튼, 모든 멤버들의 준비 값이 1이면 게임 start
    TextView settings;  //설정 아이콘

    //recyclerView
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Members> items = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);



        // textCode값은 DB에서 가져온 Code값으로 설정

        //gameStart는 준비상태가 모두 '1'일때 활성화

        //settings 설정

        //settings.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent = new Intent(CreateRoom.this, SettingsActivity.class);
        //        startActivity(intent);
        //    }
        //});

        //recyclerView

        recyclerView = (RecyclerView)findViewById(R.id.memberList_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        items.clear();
        items.add(new Members("Cha", false));
        items.add(new Members("Dong", true));


        customAdapter = new CustomAdapter(items);
        recyclerView.setAdapter(customAdapter);

        //customAdapter.notifyDataSetChanged();

    }

}