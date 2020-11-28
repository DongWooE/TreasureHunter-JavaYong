package com.captech.ar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    Button btn_backtorule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_backtorule = findViewById(R.id.btn_backtorule);

        btn_backtorule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,GameRuleActivity.class);
                startActivity(intent);      //게임 룰에서 AR화면으로 이동
                finish();
            }
        });
    }
}