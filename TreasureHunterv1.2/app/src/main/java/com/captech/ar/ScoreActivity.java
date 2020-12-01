package com.captech.ar;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Maininterface.StartActivity;

public class ScoreActivity extends AppCompatActivity {
    SoundPool soundPool;
    int buttonsound;

    TextView Scoreview;
    ImageView btn_gotomain;

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
                //버튼을 누를때마다 효과음 발생
                soundPool.play(buttonsound,1,1,1,0,1);

                Intent intent = new Intent(ScoreActivity.this, StartActivity.class);
                startActivity(intent);      //게임 룰에서 게임화면으로 이동
                finish();

                GameRuleActivity.setFindingtime =0;  //모든 전역변수들을 초기화
                GameRuleActivity.setHidingtime =0;
                GameRuleActivity.userNickName = null;
                MainActivity.score =0;
                GameRuleActivity.isNext = false;
            }
        });


    }
    @Override
    public void onBackPressed() {}

    @Override
    protected void onStart() {
        super.onStart();
        //버튼사운드 할당
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(6)
                    .build();
        }
        else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }

        buttonsound = soundPool.load(getApplicationContext(),R.raw.button,1);

    }

    //버튼 사운드 해제
    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }

}