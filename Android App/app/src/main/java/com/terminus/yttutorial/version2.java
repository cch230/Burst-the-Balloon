package com.terminus.yttutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.SimpleDateFormat;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.Texture;

import java.util.Date;
import java.util.Random;

import static android.widget.Toast.makeText;

public class version2 extends AppCompatActivity {
    public static final String RANK2= "my rank02";
    public static final String RECODE= "recode rank2";
    private long backKeyPressedTime = 0;
    private Scene scene;
    private View view1,view2;
    private Camera camera;
    private Button mainmenu;
    private ModelRenderable bulletRenderable;
    private boolean shouldStartTimer = true;
    private int shooting_balloons=0;
    private Point point;
    private TextView balloonsLeftTxt, textView,time_text;
    private SoundPool soundPool;
    private int sound;
    private Boolean sig=false;
    private Intent intent;
    private ImageView shoot;
    private Toast toast;
    String value="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getRealSize(point);

        setContentView(R.layout.activity_version2);

        loadSoundPool();
        mainmenu= (Button) findViewById(R.id.rebtn2);
        balloonsLeftTxt = findViewById(R.id.balloonsCntTxt);
        textView = (TextView) findViewById(R.id.text1);
        time_text = (TextView) findViewById(R.id.time_text1);
        CustomArFragment arFragment =
                (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);


        scene = arFragment.getArSceneView().getScene();
        camera = scene.getCamera();

        addBalloonsToScene();
        buildBulletModel();

        view1= (View) findViewById(R.id.v1);
        view2= (View) findViewById(R.id.v2);
        shoot = findViewById(R.id.shootButton);
        shoot.setOnClickListener(v -> {

            if (shouldStartTimer) {
                startTimer();
                shouldStartTimer = false;
            }
            shoot.setImageResource(R.drawable.re_images);
            shoot();
        });
        if(sig==true||shooting_balloons==100){

        }
    }

    protected void onStop(){
        super.onStop();

    }

    protected void onPause(){
        super.onPause();

    }

    private void loadSoundPool() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        sound = soundPool.load(this, R.raw.blop_sound, 1);

    }

    private void shoot() {

        Ray ray = camera.screenPointToRay(point.x / 2f, point.y / 2f);
        Node node = new Node();
        node.setRenderable(bulletRenderable);
        scene.addChild(node);

        new Thread(() -> {

            for (int i = 0;i < 200;i++) {

                int finalI = i;
                runOnUiThread(() -> {

                    Vector3 vector3 = ray.getPoint(finalI * 0.1f);
                    node.setWorldPosition(vector3);

                    Node nodeInContact = scene.overlapTest(node);

                    if (nodeInContact != null) {

                        shooting_balloons++;
                        balloonsLeftTxt.setText("맞힌 풍선: " + shooting_balloons);
                        scene.removeChild(nodeInContact);

                        soundPool.play(sound, 1f, 1f, 1, 0
                                , 1f);

                    }

                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            runOnUiThread(() -> scene.removeChild(node));

        }).start();

    }

    private void startTimer() {

        TextView timer = findViewById(R.id.timerText);
        int ms=1000;
        new Thread(() -> {

            int seconds = 9;
            int millisecond=10000;
            while (millisecond > 0) {

                try {
                    Thread.sleep(1);
                    millisecond--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int secondsPassed = millisecond/1000;
                int millisecondsPassed = millisecond%1000;

                runOnUiThread(() -> timer.setText(secondsPassed + ":" + millisecondsPassed));
                if(millisecond==0) {
                   sig=true;
                }

            }
            if(sig==true){
                SharedPreferences settings = getSharedPreferences(RANK2, 0);
                SharedPreferences.Editor editor = settings.edit();
                int best= Integer.parseInt(settings.getString("value", ""));
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                mainmenu.setVisibility(View.VISIBLE);
                time_text.setText(String.valueOf(shooting_balloons));
                shoot.setVisibility(View.GONE);
                Long time = System.currentTimeMillis(); //시간 받기
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //포멧 변환  형식 만들기
                Date dd = new Date(time); //받은 시간을 Date 형식으로 바꾸기
                String strTime = sdf.format(dd);//Data 정보를 포멧 변환하기
                SharedPreferences sharedPreferences = getSharedPreferences(RECODE, 0);
                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                editor2.putString(strTime, String.valueOf(shooting_balloons));
                editor2.commit();
                if(shooting_balloons>best){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("CRAZY!!!");
                    textView.setTextSize(72);
                    textView.setTextColor(Color.argb(255,126,87,194));
                    editor.putString("value", String.valueOf(shooting_balloons));
                    editor.commit();
                }
                else {
                    if (shooting_balloons > 25) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("UNBELIEVABLE!!");
                        textView.setTextSize(40);
                        textView.setTextColor(Color.RED);
                    } if (shooting_balloons <= 25 && shooting_balloons>20) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("PERFECT!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } if (shooting_balloons <= 20 && shooting_balloons>15) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("GREAT!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } if (shooting_balloons <= 15 && shooting_balloons>10) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("GOOD!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } if (shooting_balloons <= 10) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("BAD!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    }
                }

            }
        }).start();

    }

    private void buildBulletModel() {
        Texture
                .builder()
                .setSource(this, R.drawable.texture)
                .build()
                .thenAccept(texture -> {
                    MaterialFactory
                            .makeOpaqueWithTexture(this, texture)
                            .thenAccept(material -> {

                                bulletRenderable = ShapeFactory
                                        .makeSphere(0.01f,
                                                new Vector3(0f, 0f, 0f),
                                                material);
                            });
                });
    }

    private void addBalloonsToScene() {

        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("balloon.sfb"))
                .build()
                .thenAccept(renderable -> {
                    int x,y,z;
                    Float a,b,c;
                    for (int i = 0;i < 100;i++) {

                        Node node = new Node();
                        node.setRenderable(renderable);
                        scene.addChild(node);


                        Random random = new Random();
                        a = random.nextFloat();
                        b = random.nextFloat();
                        c = random.nextFloat();
                        x = random.nextInt(20);
                        y = random.nextInt(10);
                        z = random.nextInt(10);
                        x-=10;
                        y-=5;
                        z-=15;
                        a+=x;
                        b+=y;
                        c+=z;
                        node.setWorldPosition(new Vector3(a,b,c));
                    }
                });
    }

    public void onClick(View view) {
        if(view==mainmenu){
            intent = new Intent(version2.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();

            toast=makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            finish();
            toast.cancel();
            toast= makeText(this,"또 놀러오세요ㅠㅠ",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
