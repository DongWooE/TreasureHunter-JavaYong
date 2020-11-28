package com.captech.ar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Maininterface.StartActivity;

public class GameRuleActivity extends AppCompatActivity {

    //전역변수들
    public static String userNickName;
    public static Integer setFindingtime;
    public static Integer setHidingtime;


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
                show();
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

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set , null);
        builder.setView(view);
        final EditText etNick = view.findViewById(R.id.etNick);
        final EditText etHide = view.findViewById(R.id.etHide);
        final EditText etFind = view.findViewById(R.id.etFind);
        final Button buttonSet = view.findViewById(R.id.buttonSet);

        final AlertDialog dialog = builder.create();
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer rt1 = 60, rt2 =60, rt3=5;
                userNickName = etNick.getText().toString();
                String stringHide = etHide.getText().toString();
                String stringFind = etFind.getText().toString();


                boolean isNext = false;
                try {
                    setFindingtime = Integer.parseInt(stringHide);
                    setHidingtime = Integer.parseInt(stringFind);

                    isNext = true;
                } catch (NumberFormatException e) {
                    isNext = false;
                    Toast.makeText(getApplicationContext(), "닉네임을 제외하고 모두 숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                if(isNext) {
                    Intent intent = new Intent(GameRuleActivity.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }

}

