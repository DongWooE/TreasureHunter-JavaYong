package Maininterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.captech.ar.GameRuleActivity;
import com.captech.ar.GameRuleActivity;
import com.captech.ar.R;



import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//어플의 가장 초기화면 by 이동우
public class StartActivity extends AppCompatActivity {

    Button btn_start;
    Button btn_multi;
    Button btn_exit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);    

        //xml 파일의 버튼들과 연결
        btn_start = findViewById(R.id.btn_start);
        btn_multi = findViewById(R.id.btn_multi);
        btn_exit = findViewById(R.id.btn_exit);

        //btn_start(혼자 하기) 버튼을 눌렀을떄의 이밴트
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 액티비티에서 GameRuleActivity로 이동
                Intent intent = new Intent(StartActivity.this, GameRuleActivity.class); 
                startActivity(intent);  
                finish();
            }
        });
        
        //btn_multi(같이 하기) 버튼을 눌렀을떄의 이밴트
        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아직 구현되지않은 부분이므로 toast message 출력
                Toast.makeText(StartActivity.this, "지금은 준비중이예요", Toast.LENGTH_SHORT).show();
            }
        });

        //btn_exit( 나가기 ) 버튼을 눌렀을떄의 이밴트
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //액티비티 종료
                finish();
            }
        });

    }

}