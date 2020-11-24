package com.example.javayongtest.MainInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.javayongtest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SelectMode extends AppCompatActivity {


    Button singleButton;
    Button multiButton;

    //파이어베이스 연동을 위한 데이터
    private DatabaseReference mDBReference = null;
    private HashMap<String, Object> childUpdate = null;
    private Map<String, Object> userValue = null;
    private User user = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        //싱글 버튼을 누를때 이벤트 - 바로 게임으로 진행
        singleButton = findViewById(R.id.singleButton);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder nickName = new AlertDialog.Builder(SelectMode.this);
                nickName.setTitle("NICKNAME");
                nickName.setMessage("Please Enter your nickname");

                final EditText et = new EditText(SelectMode.this);
                nickName.setView(et);

                nickName.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userName = et.getText().toString();   //DB로 사용자 ID 옮기기

                        // dialog에서 받은 userName을 String으로 저장 후 데이터베이스에 저장
                        mDBReference = FirebaseDatabase.getInstance().getReference();
                        childUpdate = new HashMap<>();
                        user = new User(userName, 0);
                        userValue = user.toMap();

                        childUpdate.put("/User " + userName, user);
                        mDBReference.updateChildren(childUpdate);

                        Intent intent = new Intent(SelectMode.this, ExampleForFirebase.class);
                        startActivity(intent);

                        dialogInterface.dismiss();
                    }
                });

                nickName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                nickName.show();
            }
        });

        //멀티 버튼을 누를때 이벤트
        multiButton = findViewById(R.id.multiButton);
        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectMode.this, MainPage.class);
                startActivity(intent);
            }
        });


    }
}