package com.example.javayongtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateRoom extends AppCompatActivity {

    Button textCode;  //방에서 코드값이 출력되는 문구
    Button gameStart;  //게임 시작 버튼, 모든 멤버들의 준비 값이 1이면 게임 start
    TextView settings;  //설정 아이콘

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


    }


}