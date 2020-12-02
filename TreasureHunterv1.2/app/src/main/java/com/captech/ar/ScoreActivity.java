package com.captech.ar;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Maininterface.StartActivity;



public class ScoreActivity extends AppCompatActivity {

    public static MediaPlayer rmediaPlayer;
    SoundPool csoundPool;
    int cbuttonsound;


    TextView Scoreview;
    ImageView btn_gotomain;

    //액티비티가 종료될때 이 메소드를 실행함 (배경음 해제)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rmediaPlayer != null){
            rmediaPlayer.release();
            rmediaPlayer=null;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        rmediaPlayer = MediaPlayer.create(ScoreActivity.this,R.raw.scoresound);
        rmediaPlayer.start();

        btn_gotomain = findViewById(R.id.btn_gotomain);

        Scoreview = findViewById(R.id.Scoreview);
        Scoreview.setText(MainActivity.score+"");

        btn_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csoundPool.play(cbuttonsound,1,1,0,0,0);

                Intent intent = new Intent(ScoreActivity.this, StartActivity.class);
                startActivity(intent);      //게임 룰에서 게임화면으로 이동


                GameRuleActivity.setFindingtime =0;  //모든 전역변수들을 초기화
                GameRuleActivity.setHidingtime =0;
                GameRuleActivity.userNickName = null;
                MainActivity.score =0;
                GameRuleActivity.isNext = false;

                if(rmediaPlayer.isPlaying()){
                    rmediaPlayer.stop();
                    rmediaPlayer.reset();
                }

                finish();
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

            csoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(1)
                    .build();
        }
        else {
            csoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        cbuttonsound = csoundPool.load(getApplicationContext(),R.raw.button,0);


    }

    //버튼 사운드 해제
    @Override
    protected void onStop() {
        super.onStop();
        csoundPool.release();
    }

}