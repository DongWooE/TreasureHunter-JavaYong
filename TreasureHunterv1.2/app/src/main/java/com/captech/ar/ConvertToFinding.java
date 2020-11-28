package com.captech.ar;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConvertToFinding extends AppCompatActivity {

    public static Integer num1;
    Button btn_convert;
//public static int isbutton=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_finding);

        btn_convert=findViewById(R.id.btn_convert);
        btn_convert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }
}