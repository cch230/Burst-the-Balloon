package com.terminus.yttutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import static android.widget.Toast.makeText;


public class version1 extends AppCompatActivity {
    public static final String RANK= "my rank01";
    public static final String RECODE= "recode rank1";
    private long backKeyPressedTime = 0;
    private LinearLayout layout,layout1;
    private ImageButton explodeButton;
    private Button btn,mainmenu;
    private ImageView ani;
    private Intent intent;
    private TextView time,textView,time_text,rank;
    private Toast toast;
    AnimationDrawable animation;
    float i=1, val_clear=0.0f;
    int val=10, val2=0, flag=0;
    String value="0";
    private Boolean signal=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version1);
        rank = (TextView) findViewById(R.id.rank);
        textView = (TextView) findViewById(R.id.text);
        time = (TextView) findViewById(R.id.time);
        time_text = (TextView) findViewById(R.id.time_text);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        explodeButton = (ImageButton) findViewById(R.id.explode);
        btn= (Button) findViewById(R.id.btn);
        mainmenu= (Button) findViewById(R.id.rebtn);
        ani = (ImageView) findViewById(R.id.ani);
        animation=(AnimationDrawable) ani.getBackground();
        intent=getIntent();
        textView.setVisibility(View.GONE);
        time_text.setVisibility(View.GONE);
        SharedPreferences settings = getSharedPreferences(RANK, MODE_PRIVATE);
        value= settings.getString("value", "");
        rank.setText(value);
        int x;
        Random random = new Random();

        x = random.nextInt(6);
        switch (x){
            case 0:
                explodeButton.setImageResource(R.drawable.redballoon);
                break;
            case 1:
                explodeButton.setImageResource(R.drawable.yellowballoon);
                break;
            case 2:
                explodeButton.setImageResource(R.drawable.pinkballoon);
                break;
            case 3:
                explodeButton.setImageResource(R.drawable.skyblueballoon);
                break;
            case 4:
                explodeButton.setImageResource(R.drawable.puppyballoon);
                break;
            default:
                explodeButton.setImageResource(R.drawable.aaa);
                break;
        }

      //  transaction.addToBackStack(null);

    }

    public void onClick(View view){
        if(view==btn){
            flag=1;
            time.setText(Integer.toString(val));
            btn.setVisibility(View.GONE);
            new Thread(new Runnable() {
                public void run() {
                    while (val > 0 && i<4.5) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        }
                        val2++;
                        textView.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                        if(val2%800==0)
                            val--;
                        val_clear=val2/800.0f;
                        time.setText(Integer.toString(val));
                        if(val<3)
                            time.setTextColor(Color.RED);
                    }
                }
            }).start();
        }

        if(view==mainmenu){
            intent = new Intent(version1.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public boolean onTouchEvent (MotionEvent event){
        String time_num;
        float newtime, oldtime;
        if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1) {
            animation.start();
            i += 0.05;
            // Toast.makeText(getApplicationContext(),Float.toString(i),Toast.LENGTH_SHORT).show();
            explodeButton.setScaleX(i);
            explodeButton.setScaleY(i);
            if (i > 4.5) {
                animation.stop();
                TransitionManager.beginDelayedTransition(layout, new Explode());
                explodeButton.setVisibility(View.GONE);
                ////
                time_text.setVisibility(View.VISIBLE);
                time_num = String.format("%.3f", val_clear);
                time_text.setText(time_num);
                time_text.setTextColor(Color.BLACK);
                newtime = Float.parseFloat(time_num);
                if (!rank.getText().toString().equals("")) {
                    oldtime = Float.parseFloat(rank.getText().toString());
                } else
                    oldtime = newtime;
                Long time = System.currentTimeMillis(); //시간 받기
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //포멧 변환  형식 만들기
                Date dd = new Date(time); //받은 시간을 Date 형식으로 바꾸기
                String strTime = sdf.format(dd);//Data 정보를 포멧 변환하기
                SharedPreferences sharedPreferences = getSharedPreferences(RECODE, 0);
                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                time_num = String.format("%.3f", newtime);
                if(!signal){
                    signal=true;
                    editor2.putString(strTime, time_num);
                    editor2.commit();
                }

                if (newtime <= oldtime) {
                    rank.setText(time_num);
                    rank.setTextColor(Color.RED);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("CRAZY!!!");
                    textView.setTextSize(72);
                    textView.setTextColor(Color.argb(255,126,87,194));
                    mainmenu.setVisibility(View.VISIBLE);
                    SharedPreferences settings = getSharedPreferences(RANK, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor = settings.edit();
                    editor.putString("value", time_num);
                    editor.commit();
                }
                else{
                    mainmenu.setVisibility(View.VISIBLE);
                    if (newtime < 5 && newtime >= 0) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("UNBELIEVABLE!!");
                        textView.setTextSize(40);
                        textView.setTextColor(Color.RED);
                    } else if (newtime < 6) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("PERFECT!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } else if (newtime < 7) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("GREAT!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } else if (newtime < 8) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("GOOD!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    } else if (newtime >= 8 && newtime < 10) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("BAD!!");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(72);
                    }
                }

            }
            if(i<4.5 && val==0) {
                animation.stop();
                TransitionManager.beginDelayedTransition(layout, new Fade());
                explodeButton.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("FAIL...");
                textView.setTextColor(Color.RED);
                textView.setTextSize(72);
                mainmenu.setVisibility(View.VISIBLE);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            animation.stop();
        }
        return  super.onTouchEvent(event);
    }
    protected void onStop(){
        super.onStop();
    }

    protected void onPause(){
        super.onPause();
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
