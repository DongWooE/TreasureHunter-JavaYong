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

package com.captech.ar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArFragment mFragment;
    private GestureDetector mGestureDetector;
    private ImageView postImageView;
    private ConstraintLayout editTextConstraintLayout;
    private EditText editTextField;
    private Button saveTextButton;
    private FloatingActionButton fab;
    private int selectedId = -1;

    //여기는 타이머 변수
    private int count=30;    //카운트다운 시작숫자
    private TextView TextViewMain;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            TextViewMain.setText(count + ""); //int형으로 넣으면 오류나고 뒤에 ""붙여서 스트링으로
            count -= 1;
            if (count <= 0) {
                isFindingTreasure=true;
                Intent intent=new Intent(MainActivity.this,ConvertToFinding.class);
                startActivity(intent);
                handler.removeCallbacks(runnable);
                TextViewMain.setText(""); //int형으로 넣으면 오류나고 뒤에 ""붙여서 스트링으로
            } else {
                handler.postDelayed(runnable, 1000);
            }
        }
    };

    //user정보에 대한 변수
    private String UserNickname; //user이름
    private int setHidingtime; //보물을 숨기는 시간
    private int setFindingtime; //보물을 찾는 시간
    private int setNumberOfTreasure; //보물의 개수
    private int score; //점수
    private boolean isFindingTreasure=false; //보물을 찾고 있는가?
    private TextView UserInfo; //user정보
    private String ISFINDINGTREASURE; //보물을 숨기고 있으면 "보물을 숨기는 중"
    private Handler UIhandler = new Handler();

    private Runnable UIrunnable = new Runnable() {
        @Override
        public void run() {
            UserInfo.setText("닉네임 : " + UserNickname+"\n"+
                    "보물을 숨기는 시간 : "+setHidingtime+"\n"+
                    "보물을 찾는 시간 : "+setFindingtime+"\n"+
                    "보물의 갯수 : "+setNumberOfTreasure+"\n"+
                    UserNickname+"의 점수 : "+score+"\n"+
                    ISFINDINGTREASURE+"\n");
            if(isFindingTreasure){
                ISFINDINGTREASURE="보물을 찾는 중";
                //UIhandler.postDelayed(UIrunnable,0);
            }
            else{
                ISFINDINGTREASURE="보물을 숨기는 중";
                //UIhandler.removeCallbacks(UIrunnable);
            }
            UIhandler.postDelayed(UIrunnable,0);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab = findViewById(R.id.fab);
        postImageView = findViewById(R.id.postIcon);
        editTextConstraintLayout = findViewById(R.id.changePostItTextConstraintLayout);
        saveTextButton = findViewById(R.id.saveTextButton);
        editTextField = findViewById(R.id.editTextField);
        mFragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);


        //on tapping of the scene, we want to interact with the world
        mFragment.getArSceneView().getScene().setOnTouchListener((hitTestResult, motionEvent) -> mGestureDetector.onTouchEvent(motionEvent));
        //여기서부터 타이머
        TextViewMain = findViewById(R.id.TextViewMain);
        TextViewMain.setText("");   //1초 간격으로 출력
        handler.post(runnable);
        //여기까지 타이머

        //userinfo start
        UserInfo=findViewById(R.id.UserInfo);
        UserInfo.setText("");
        handler.post(UIrunnable);
        //userinfo end

        mGestureDetector =
                new GestureDetector(
                        this,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onSingleTapUp(MotionEvent e) {
                                tapAddObject(e);
                                return true;
                            }

                            @Override
                            public boolean onDown(MotionEvent e) {
                                return true;
                            }
                        });


        //take a photo on clicking of the fab
        fab.setOnClickListener(view -> PhotoUtils.takePhoto(mFragment));

        //click listener for selecting that you want to post a note.
        postImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
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
        alBuilder.setMessage("Want to Close App?");

        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
    private void tapAddObject(MotionEvent motionEvent) {
        Frame frame = mFragment.getArSceneView().getArFrame();

        if (selectedId == -1 || motionEvent == null || frame == null ||
                frame.getCamera().getTrackingState() != TrackingState.TRACKING)
            return;


        for (HitResult hit : frame.hitTest(motionEvent)) {
            Trackable trackable = hit.getTrackable();
            if ((trackable instanceof Plane &&
                    ((Plane) trackable).isPoseInPolygon(hit.getHitPose()))) {
                //set the 3d model to the anchor
                buildRenderable(mFragment, hit.createAnchor());

                //remove selected item after a successful set.
                selectedId = -1;
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
    private void buildRenderable(ArFragment fragment, Anchor anchor) {
        ModelRenderable.builder()
                .setSource(fragment.getContext(), Uri.parse("post_it.sfb"))
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
    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode postitNode = new TransformableNode(fragment.getTransformationSystem());
        postitNode.setRenderable(renderable);
        postitNode.setParent(anchorNode);

        //rotate the post it to stick to the flat surface.
        postitNode.setLocalRotation(new Quaternion(.65f, 0f, 0f, -.5f));

        //add text view node
        ViewRenderable.builder().setView(this, R.layout.post_it_text).build()
                .thenAccept(viewRenderable -> {
                    Node noteText = new Node();
                    noteText.setParent(fragment.getArSceneView().getScene());
                    noteText.setParent(postitNode);
                    noteText.setRenderable(viewRenderable);
                    noteText.setLocalPosition(new Vector3(0.0f, -0.05f, 0f));
                });

        //adding a tap listener to change the text of a note
        postitNode.setOnTapListener((hitTestResult, motionEvent) -> {
            //select it on touching so we can rotate it and position it as needed
            postitNode.select();

            //toggle the edit text view.
            if (editTextConstraintLayout.getVisibility() == View.GONE) {
                editTextConstraintLayout.setVisibility(View.VISIBLE);

                //save the text when the user wants to
                saveTextButton.setOnClickListener(view -> {
                    TextView tv;
                    for (Node nodeInstance : postitNode.getChildren()) {
                        if (nodeInstance.getRenderable() instanceof ViewRenderable) {
                            tv = ((ViewRenderable) nodeInstance.getRenderable()).getView().findViewById(R.id.postItNoteTextView);
                            tv.setText(editTextField.getText());
                            editTextConstraintLayout.setVisibility(View.GONE);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            break;
                        }
                    }
                });
            } else {
                editTextConstraintLayout.setVisibility(View.GONE);
            }
        });


        fragment.getArSceneView().getScene().addChild(anchorNode);
        postitNode.select();
    }

}
