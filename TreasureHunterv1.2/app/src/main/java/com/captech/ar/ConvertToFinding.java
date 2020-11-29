package com.captech.ar;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import Maininterface.StartActivity;

public class ConvertToFinding extends AppCompatActivity { // 보물을 숨기는 시간이 모두 지나면 화면을 전환하는 메소드 by 김동용

    ImageView btn_convert; //화면전환 버튼

    @Override //오버라이드
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_finding); //XML파일 화면구성으로 화면을 출력
        btn_convert=findViewById(R.id.btn_convert); //버튼연결
        btn_convert.setOnClickListener(new View.OnClickListener(){ //버튼클릭이 감지되면
            @Override //오버라이드
            public void onClick(View v) { //버튼을 클릭하면
                setResult(RESULT_OK); //startActivityForResult, 값 비교를 위해 RESULT_OK를 반환 by 이동우
                finish(); //메소드 종료
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}