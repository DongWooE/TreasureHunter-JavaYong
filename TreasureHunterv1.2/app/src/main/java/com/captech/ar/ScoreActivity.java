package com.captech.ar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Maininterface.StartActivity;

public class ScoreActivity extends AppCompatActivity {
    TextView Scoreview;
    Button btn_gotomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        btn_gotomain = findViewById(R.id.btn_gotomain);

        Scoreview = findViewById(R.id.Scoreview);
        Scoreview.setText(MainActivity.score+"");

        btn_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, StartActivity.class);
                startActivity(intent);      //게임 룰에서 게임화면으로 이동
                finish();

                GameRuleActivity.setFindingtime =0;  //모든 전역변수들을 초기화
                GameRuleActivity.setHidingtime =0;
                GameRuleActivity.userNickName = null;
                MainActivity.score =0;
            }
        });
    }

}