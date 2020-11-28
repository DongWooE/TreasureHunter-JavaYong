package com.captech.ar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Maininterface.StartActivity;

public class GameRuleActivity extends AppCompatActivity {

    Button btn_move;
    Button btn_setting;
    Button btn_backtomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rule);

        btn_move = findViewById(R.id.btn_move);
        btn_setting = findViewById(R.id.btn_setting);
        btn_backtomain = findViewById(R.id.btn_backtomain);


        btn_move.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GameRuleActivity.this,MainActivity.class);
                startActivity(intent);      //게임 룰에서 게임화면으로 이동
               finish();
           }
       });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameRuleActivity.this,SettingActivity.class);
                startActivity(intent);      //게임 룰에서 설정화면으로 이동
                finish();
            }
        });

        btn_backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameRuleActivity.this, StartActivity.class);
                startActivity(intent);      //게임 룰에서 메인화면으로 이동
                finish();
            }
        });
    }
}