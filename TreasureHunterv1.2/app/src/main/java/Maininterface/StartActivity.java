package Maininterface;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.captech.ar.GameRuleActivity;
import com.captech.ar.GameRuleActivity;
import com.captech.ar.R;



import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//어플의 가장 초기화면 by 이동우
public class StartActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    int buttonsound;
    int mapsound;

    TextView btn_start;
    TextView btn_multi;
    TextView btn_exit;


    AnimationDrawable shipAnimation;



    //액티비티가 종료될때 이 메소드를 실행함
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        mediaPlayer = MediaPlayer.create(StartActivity.this,R.raw.mainbgm);
        mediaPlayer.start();

        //xml 파일의 버튼들과 연결
        btn_start = findViewById(R.id.btn_start);
        btn_multi = findViewById(R.id.btn_multi);
        btn_exit = findViewById(R.id.btn_exit);

        //btn_start(혼자 하기) 버튼을 눌렀을떄의 이벤트
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼을 누를때마다 효과음 발생
                //soundPool.play(mapsound,1,1,1,0,1);
                //현재 액티비티에서 GameRuleActivity로 이동
                Intent intent = new Intent(StartActivity.this, GameRuleActivity.class);
                startActivity(intent);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }

                finish();
            }
        });


        
        //btn_multi(같이 하기) 버튼을 눌렀을떄의 이벤트
        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼을 누를때마다 효과음 발생
                soundPool.play(buttonsound,1,1,1,0,1);
                //아직 구현되지않은 부분이므로 toast message 출력
                Toast.makeText(StartActivity.this, "지금은 준비중이예요", Toast.LENGTH_SHORT).show();
            }
        });

        //btn_exit( 나가기 ) 버튼을 눌렀을떄의 이벤트
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼을 누를때마다 효과음 발생
                soundPool.play(buttonsound,1,1,1,0,1);
                ActivityCompat.finishAffinity(StartActivity.this);
                System.exit(0); //어플리케이션 종료
            }
        });

    }


    //시작화면에 애니메이션을 활용한 배경화면 설정 By 박성진.
    @Override
    public void onStart()
    {
        super.onStart();
        ImageView ship = (ImageView) findViewById(R.id.ship);
        ship.setBackgroundResource(R.drawable.background);
        shipAnimation = (AnimationDrawable) ship.getBackground();
        shipAnimation.start();

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
        mapsound = soundPool.load(getApplicationContext(),R.raw.openmap,1);
    }

    //버튼 사운드 해제
    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }


    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(StartActivity.this);
        System.exit(0); //어플리케이션 종료
    }

}