package com.captech.ar;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Maininterface.StartActivity;

// 사용자에게 간단한 메세지를 보여주고 각종 설정들을 입력받는 class by 차재현 & 이동우.
public class GameRuleActivity extends AppCompatActivity {

    //현재 액티비티에서 받은 사용자의 정보들을 뒤의 액티비티에서도 사용할 수 있도록 static 으로 선언
    public static String userNickName;
    public static Integer setFindingtime;
    public static Integer setHidingtime;

    //Button btn_move;
    ImageView btn_setting;
    ImageView btn_backtomain;

    public static boolean isNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rule);


        btn_setting = findViewById(R.id.btn_setting);
        btn_backtomain = findViewById(R.id.btn_backtomain);


        // 설정 버튼을 눌렀을때 사용자에게 EditText를 사용해서 각종 정보들을 받는 부분 by 이동우
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
                if(isNext) finish();
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

    //사용자에게 설정들을 EditText를 통해서 받는 함수 by 이동우
    public void show(){
        //AlertDialog 만들고 LayoutInflater 클래스를 사용해서 dialog_set.xml과 연동하는 부분
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set , null);
        builder.setView(view);

        //버튼과 EditText 부분을 xml의 객체들과 연동
        final EditText etNick = view.findViewById(R.id.etNick);
        final EditText etHide = view.findViewById(R.id.etHide);
        final EditText etFind = view.findViewById(R.id.etFind);
        final ImageView buttonSet = view.findViewById(R.id.buttonSet);

        final AlertDialog dialog = builder.create();

        //다음으로 가기 버튼을 눌렀을때의 클릭 콜백
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Interger 변수들을 초기화
                Integer rt1 = 60, rt2 =60, rt3=5;

                //editText를 통해서 받은 부분들을 String 객체에 저장
                userNickName = etNick.getText().toString();
                String stringHide = etHide.getText().toString();
                String stringFind = etFind.getText().toString();

                    // 사용자에게 정수형으로 받아야할 부분들을 String으로 받았을 때를 방지
                try {
                    //String으로 받은 숨기는 시간, 찾는 시간들을 Integer 값으로 변환
                    setFindingtime = Integer.parseInt(stringFind);
                    setHidingtime = Integer.parseInt(stringHide);
                    isNext = true;
                    if(userNickName.getBytes().length <= 0) {
                        userNickName = "user";
                        Toast.makeText(getApplicationContext(), "nickname : user(default)", Toast.LENGTH_SHORT).show();
                    }
                    //만약에 숫자값이 아니라면 toast message를 띄우고 isNext를 false로 설정하여 다음 액티비티로 넘어가는 것을 방지
                } catch (NumberFormatException e) {
                    isNext = false;
                    Toast.makeText(getApplicationContext(), "닉네임을 제외하고 모두 숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                //사용자로부터 정수 값으로 받아야할 부분들을 잘 받았을시에 MainActivity로 이동하고 dialog 종료
                if(isNext) {
                    Intent intent = new Intent(GameRuleActivity.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();

                }

            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("게임을 종료하시겠습니까?");

        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameRuleActivity.isNext = false;
                ActivityCompat.finishAffinity(GameRuleActivity.this);
                System.exit(0); //어플리케이션 종료
            }
        });

        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alBuilder.setTitle("게임 종료");
        alBuilder.show();}

}

