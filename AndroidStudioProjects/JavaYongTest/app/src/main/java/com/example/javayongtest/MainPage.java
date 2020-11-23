package com.example.javayongtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainPage extends AppCompatActivity {

    Button roomCreate;
    Button roomEnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        roomCreate = findViewById(R.id.roomCreate);
        roomEnter = findViewById(R.id.roomEnter);

        roomCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder nickName = new AlertDialog.Builder(MainPage.this);
                nickName.setTitle("NICKNAME");
                nickName.setMessage("Please Enter your nickname");

                final EditText et = new EditText(MainPage.this);
                nickName.setView(et);

                nickName.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = et.getText().toString();   //DB로 사용자 ID 옮기기


                        Intent intent = new Intent(MainPage.this, CreateRoom.class);
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

        roomEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder nickName = new AlertDialog.Builder(MainPage.this);
                nickName.setTitle("NICKNAME");
                nickName.setMessage("Please Enter your nickname");

                final EditText et = new EditText(MainPage.this);
                nickName.setView(et);

                nickName.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = et.getText().toString();   //DB로 사용자 ID 옮기기

                        AlertDialog.Builder enterCode = new AlertDialog.Builder(MainPage.this);
                        enterCode.setTitle("CODE");
                        enterCode.setMessage("Please Enter your Room Code ");

                        final EditText et_1 = new EditText(MainPage.this);
                        enterCode.setView(et_1);

                        enterCode.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String code = et_1.getText().toString();  //Code값을 DB로 넘긴다

                                Intent intent = new Intent(MainPage.this, EnterRoom.class);
                                startActivity(intent);

                                dialogInterface.dismiss();
                            }
                        });
                        enterCode.show();

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


}
}