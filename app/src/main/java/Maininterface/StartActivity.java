package Maininterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.captech.ar.MainActivity;
import com.captech.ar.R;



import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class StartActivity extends AppCompatActivity {

    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}