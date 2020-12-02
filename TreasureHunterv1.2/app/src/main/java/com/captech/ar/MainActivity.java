/*
 * Copyright 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//이거시 구데기
package com.captech.ar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {   //MainActivity class

    public static MediaPlayer mmediaPlayer;
    private ArFragment mFragment;
    private GestureDetector mGestureDetector;
    private ImageView postImageView;
    private ConstraintLayout editTextConstraintLayout;
    private EditText editTextField;
    private Button saveTextButton;
    private int selectedId = -1;
    static final int REQUEST_CODE = 100;

    public static SoundPool mainsoundPool;
    int findsound;
    int hidesound;



    //액티비티가 종료될때 이 메소드를 실행함 (배경음 해제)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mmediaPlayer != null){
            mmediaPlayer.release();
            mmediaPlayer=null;

        }
    }

    //여기는 보물을 찾는 메소드 by 이동우, 차재현
    //setHidingtime 변수 선언
    private Integer count = GameRuleActivity.setHidingtime;    //카운트다운 시작숫자
    private TextView TextViewMain;
    private Handler Hidinghandler = new Handler();  // 보물을 숨기는 handler 선언

    private Runnable runnable = new Runnable() {  // runnable를 통해 보물을 숨기는 타이머 및 현재상태 출력 by 이동우, 차재현
        @Override
        public void run() {
            TextViewMain.setText(count + "");
            count -= 1;
            boolean except = true;

            if (count <= 0) {
                isFindingTreasure=true;
                if(setNumberOfTreasure ==0 ){  // 숨기는 사람이 보물을 지정하지 않았을때 by 이동우
                    except = false;
                    Toast.makeText(getApplicationContext(), "보물을 지정하지않았습니다", Toast.LENGTH_SHORT).show();
                    GameRuleActivity.isNext = false;
                    GameRuleActivity.setFindingtime =0;  //모든 전역변수들을 초기화
                    GameRuleActivity.setHidingtime =0;
                    GameRuleActivity.userNickName = null;
                    MainActivity.score =0;



                    Hidinghandler.removeCallbacks(runnable);    // 보물을 숨기는 handler 종료
                    finish();

                }
                if(except) {
                    Intent intent = new Intent(MainActivity.this, ConvertToFinding.class);

                    // 술래가 Finder에게 넘기는 액티비티 이후에 타이머가 흘러가는 것을 방지하기위해 startActivityForResult를 사용 by 이동우
                    startActivityForResult(intent, REQUEST_CODE);

                    Hidinghandler.removeCallbacks(runnable);
                    TextViewMain.setText(""); //int형으로 넣으면 오류나고 뒤에 ""붙여서 스트링으로
                }
            } else {
                Hidinghandler.postDelayed(runnable, 1000);
            }
        }
    };

    // 술래가 Finder에게 넘기는 액티비티 이후에 타이머가 흘러가는 것을 방지하기위한 함수 by 이동우
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            //만약 이후 액티비티에서 RESULT_OK를 넘겨받는다면 다음 타이머가 작동하도록 설정
            if(resultCode == RESULT_OK){
                FindView.setText("");
                Findinghandler.post(runnable_find);
            }
        }
    }

    //여기는 보물을 찾는 메소드 by 김동용
    private Integer count_find = GameRuleActivity.setFindingtime;    //카운트다운 시작숫자
    private TextView FindView; //텍스트뷰 변수 선언
    private Handler Findinghandler = new Handler(); //보물을 찾는 핸들러 선언

    private Runnable runnable_find = new Runnable() { //보물을 찾는 runnable 선언
        @Override
        public void run() {

            FindView.setText(count_find + ""); //int형으로 넣으면 오류나고 뒤에 ""붙여서 스트링으로  만들기
            count_find -= 1; //보물을 찾는 시간 -1
            if (count_find <= 0 || (setNumberOfTreasure == 0)) { //보물을 찾는 시간이 0이 되면


                Intent intent=new Intent(MainActivity.this,ScoreActivity.class); //intent로 점수출력하는 activity로 이동
                startActivity(intent); //intent 실행

                if(mmediaPlayer.isPlaying()){
                    mmediaPlayer.stop();
                    mmediaPlayer.reset();
                }

                isFindingTreasure=false; //보물을 찾지 않고 있다


                Findinghandler.removeCallbacks(runnable_find); //보물을 찾는 핸들러 종료
                FindView.setText(""); //텍스트뷰의 스트링 없음으로 초기화
                finish();
            } else { //보물을 찾는 시간이 1이상이면
                Findinghandler.postDelayed(runnable_find, 1000); //1초씩 지연하여 runnable작동(타이머 기능)
            }
        }
    };

    //user정보에 대한 변수 by김동용
    private int setNumberOfTreasure = 0; //보물의 개수
    public static int score = 0; //점수
    private boolean isFindingTreasure=false; //보물을 찾고 있는가?
    private TextView UserInfo; //user정보
    private TextView UserName;
    private TextView Score;
    private String ISFINDINGTREASURE = "보물을 찾는 중"; //보물을 숨기고 있으면 "보물을 숨기는 중"
    private Handler UIhandler = new Handler();

    private Runnable UIrunnable = new Runnable() { //유저정보를 우상단에 출력하는 메소드 by 김동용
        @Override
        public void run() {
            //GameRuleActivity.userNickName+"\n"
            //score+"\n"+ ISFINDINGTREASURE+"\n"+GameRuleActivity.userNickName+"의 점수 : "+
            UserInfo.setText("X"+setNumberOfTreasure); //유저정보 출력
            UserName.setText("Nickname: "+GameRuleActivity.userNickName); //유저정보 출력
            Score.setText(GameRuleActivity.userNickName+"의 점수\n"+ISFINDINGTREASURE+"\n"); //유저정보 출력
            if(isFindingTreasure) //보물을 찾고 있으면
                ISFINDINGTREASURE="보물을 찾는 중"; //문자열 변경
            else //아니면
                ISFINDINGTREASURE="보물을 숨기는 중"; //문자열 변경
            UIhandler.postDelayed(UIrunnable,0); //지연없이 실시간으로 runnable 작동
        }
    };


    @Override   //Application속 실행주기의 맨 처음에 있는 method. by 김동용,박성진,차재현,이동우
    protected void onCreate(Bundle savedInstanceState) {

        mmediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.mopz_robowack);
        mmediaPlayer.start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        postImageView = findViewById(R.id.postIcon);
        editTextConstraintLayout = findViewById(R.id.changePostItTextConstraintLayout);
        saveTextButton = findViewById(R.id.saveTextButton);
        editTextField = findViewById(R.id.editTextField);
        mFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);


        //on tapping of the scene, we want to interact with the world
        mFragment.getArSceneView().getScene().setOnTouchListener((hitTestResult, motionEvent) -> mGestureDetector.onTouchEvent(motionEvent));

        //보물을 숨기는 타이머 by 차재현
        TextViewMain = findViewById(R.id.TextViewMain);
        TextViewMain.setText("");   //1초 간격으로 출력, setText 초기값으로 초기화
        Hidinghandler.post(runnable);


        //보물을 찾는 시간으로 게임을 제어하는 코드 by 김동용
        FindView = findViewById(R.id.FindView); //보물을 찾는 시간에 대한 텍스트뷰 연결

        //유저정보를 출력하는 코드 by 김동용
        UserInfo=findViewById(R.id.UserInfo); //xml의 유저정보id와 유저정보변수를 연결
        Score=findViewById(R.id.Score); //xml의 유저정보id와 유저정보변수를 연결
        UserName=findViewById(R.id.UserName); //xml의 유저정보id와 유저정보변수를 연결
        UserInfo.setText(""); //문자열을 없음으로 초기화
        Hidinghandler.post(UIrunnable); //유저정보runnable과 핸들러 연결
        //userinfo end

        mGestureDetector =  //사용자로부터 터치 제스쳐를 받고 술래가 보물을 원하는 위치에 설치할 수 있도록 해줌 by 박성진.
                new GestureDetector(
                        this,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onSingleTapUp(MotionEvent e) {
                                if(!isFindingTreasure) {
                                    tapAddObject(e);
                                }
                                return true;
                            }

                            @Override
                            public boolean onDown(MotionEvent e) {
                                return true;
                            }
                        });



        //click listener for selecting that you want to post a note.
        postImageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {    //사용자가 앱 하단에 보물을 선택했는지 안했는지에 따라 이미지 출력의 변화 by 박성진.
        switch (view.getId()) {
            case R.id.postIcon:
                if (selectedId == R.id.postIcon) {
                    //remove selection
                    selectedId = -1;
                    postImageView.setBackground(null);
                } else {
                    //selecting a post it note
                    selectedId = R.id.postIcon;
                    postImageView.setBackground(getDrawable(R.drawable.icon_outline));
                }
                break;
        }

    }

    //백버튼을 눌렀을때
    @Override
    public void onBackPressed() {

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("메인화면으로 돌아가시겠습니까?");

        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            // 도중에 게임을 종료시에 모든 타이머와 boolean값을 변경해주는 함수 by 이동우
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GameRuleActivity.isNext = false;
                GameRuleActivity.setFindingtime =0;  //모든 전역변수들을 초기화
                GameRuleActivity.setHidingtime =0;
                GameRuleActivity.userNickName = null;
                MainActivity.score =0;

                if(!isFindingTreasure) Hidinghandler.removeCallbacks(runnable);
                Findinghandler.removeCallbacks(runnable_find);
                finish();
            }
        });

        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alBuilder.setTitle("exit");
        alBuilder.show();
    }



    /**
     * Method that takes the user's tap event and creates an anchor from it
     * to attach a renderable post it note.
     *
     * @param motionEvent
     */
    private void tapAddObject(MotionEvent motionEvent) {    //술래가 원하는 위치에 터치를 해서 보물을 놓을 수 있도록 함 by 박성진.
        Frame frame = mFragment.getArSceneView().getArFrame();

        if (selectedId == -1 || motionEvent == null || frame == null || //앱 하단에 보물이 활성화(터치)되어 있지 않거나 모션 이벤트가 발생하지 않았을 경우 그냥 아무 작업없이 리턴 by 박성진.
                frame.getCamera().getTrackingState() != TrackingState.TRACKING)
            return;


        for (HitResult hit : frame.hitTest(motionEvent)) {  //사용자의 모션 이벤트가 감지될 때마다 보물 Anchor를 만들어서 원하는 위치에 출력, 보물의 개수도 증가. by 박성진.
            Trackable trackable = hit.getTrackable();
            if ((trackable instanceof Plane &&
                    ((Plane) trackable).isPoseInPolygon(hit.getHitPose()))) {
                //set the 3d model to the anchor
                buildRenderable(mFragment, hit.createAnchor());
                setNumberOfTreasure++;
                //remove selected item after a successful set.

                mainsoundPool.play(hidesound,1,1,1,0,1);   // 보물을 숨길 때마다 효과음 출력 by 차재현

                selectedId = -1;    //보물을 설치하고 나서 앱 하단에 비활성화로 전환.
                postImageView.setBackground(null);
                break;


            }
        }
    }

    /**
     * Method to build the renderable post it note.
     *
     * @param fragment
     * @param anchor
     */
    private void buildRenderable(ArFragment fragment, Anchor anchor) {  //우리가 설정한 3D modeling 보물을 출력하는 메소드 by 박성진.
        ModelRenderable.builder()
                .setSource(fragment.getContext(), Uri.parse("model.sfb"))
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                .exceptionally((throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage())
                            .setTitle("Codelab error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return null;
                }));
    }




    /**
     * Method to take a renderable and attach it to the anchor point the user selected.
     *
     * @param fragment
     * @param anchor
     * @param renderable
     */
    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {    //보물 Anchor의 사이즈를 조절하고 보물 Anchor를 사용자가 터치하면 사자리며 점수를 획득 by 박성진.

        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode postitNode = new TransformableNode(fragment.getTransformationSystem());
        postitNode.setRenderable(renderable);
        postitNode.setParent(anchorNode);
        anchorNode.setLocalScale(new Vector3(0.1f,0.1f,0.1f));  //보물상자 스케일 조절
        //rotate the post it to stick to the flat surface.
        //postitNode.setLocalRotation(new Quaternion(.65f, 0f, 0f, -.5f));

        //add text view node
//        ViewRenderable.builder().setView(this, R.layout.post_it_text).build()
//                .thenAccept(viewRenderable -> {
//                    Node noteText = new Node();
//                    noteText.setParent(fragment.getArSceneView().getScene());
//                    noteText.setParent(postitNode);
//                    noteText.setRenderable(viewRenderable);
//                    noteText.setLocalPosition(new Vector3(0.0f, -0.05f, 0f));
//                });

        //adding a tap listener to change the text of a note
        postitNode.setOnTapListener((hitTestResult, motionEvent) -> {
            //select it on touching so we can rotate it and position it as needed
            postitNode.select();
            if(isFindingTreasure)   //보물을 찾는 모드일 때 보물을 터치하게되면 보물이 사라지고 100점을 얻는다.
            {
                mainsoundPool.play(findsound,1,1,1,0,1);   // 보물을 찾을 때마다 효과음 출력 by 차재현
                setNumberOfTreasure--;
                score += 100;
                Node nodeToRemove = hitTestResult.getNode();
                anchorNode.removeChild(nodeToRemove);
            }


            //toggle the edit text view.
//            if (editTextConstraintLayout.getVisibility() == View.GONE) {
//                editTextConstraintLayout.setVisibility(View.VISIBLE);
//
//                //save the text when the user wants to
//                saveTextButton.setOnClickListener(view -> {
//                    TextView tv;
//                    for (Node nodeInstance : postitNode.getChildren()) {
//                        if (nodeInstance.getRenderable() instanceof ViewRenderable) {
//                            tv = ((ViewRenderable) nodeInstance.getRenderable()).getView().findViewById(R.id.postItNoteTextView);
//                            tv.setText(editTextField.getText());
//                            editTextConstraintLayout.setVisibility(View.GONE);
//                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                            break;
//                        }
//                    }
//                });
//            } else {
//                editTextConstraintLayout.setVisibility(View.GONE);
//            }
        });


        fragment.getArSceneView().getScene().addChild(anchorNode);
        postitNode.select();
    }

    @Override
    public void onStart()   // 보물을 숨기고 찾을때 출력되는 효과음 할당 By 차재현.
    {
        super.onStart();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mainsoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(1)
                    .build();
        }
        else {
            mainsoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }


        findsound = mainsoundPool.load(getApplicationContext(),R.raw.touch_sound,0);
        hidesound = mainsoundPool.load(getApplicationContext(),R.raw.hidingsound,0);

    }

    //버튼 사운드 해제 by 차재현
    @Override
    protected void onStop() {
        super.onStop();
        mainsoundPool.release();
    }
}
