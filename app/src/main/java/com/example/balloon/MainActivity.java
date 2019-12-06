package com.example.balloon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balloon.R;

import java.text.Format;

public class MainActivity extends AppCompatActivity {
    public static final String RANK= "my rank";
    private LinearLayout layout,layout1;
    private ImageButton explodeButton;
    private Button btn,rebtn;
    private ImageView ani;
    private Intent intent;
    private TextView time,textView,time_text,rank;
    AnimationDrawable animation;
    float i=1, val_clear=0.0f;
    int val=10, val2=0, flag=0;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rank = (TextView) findViewById(R.id.rank);
        textView = (TextView) findViewById(R.id.text);
        time = (TextView) findViewById(R.id.time);
        time_text = (TextView) findViewById(R.id.time_text);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        explodeButton = (ImageButton) findViewById(R.id.explode);
        btn= (Button) findViewById(R.id.btn);
        rebtn= (Button) findViewById(R.id.rebtn);
        ani = (ImageView) findViewById(R.id.ani);
        animation=(AnimationDrawable) ani.getBackground();
        intent=getIntent();
        textView.setVisibility(View.GONE);
        time_text.setVisibility(View.GONE);
        rebtn.setVisibility(View.GONE);
        SharedPreferences settings = getSharedPreferences(RANK, MODE_PRIVATE);
        value= settings.getString("value", "");
        rank.setText(value);
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
        if(view==rebtn){
            intent = new Intent(MainActivity.this, emtpy.class);
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
                    if (newtime <= oldtime) {
                        time_num = String.format("%.3f", newtime);
                        rank.setText(time_num);
                        rank.setTextColor(Color.RED);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("CRAZY!!!");
                        textView.setTextSize(72);
                        textView.setTextColor(Color.argb(255,126,87,194));
                        rebtn.setVisibility(View.VISIBLE);

                    }
                    else{
                        rebtn.setVisibility(View.VISIBLE);
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
                    rebtn.setVisibility(View.VISIBLE);
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
        SharedPreferences settings = getSharedPreferences(RANK, 0);
        SharedPreferences.Editor editor = settings.edit();
        value= rank.getText().toString();
        editor.putString("value", value);
        editor.commit();
    }
    protected void onPause(){
        super.onPause();
        SharedPreferences settings = getSharedPreferences(RANK, 0);
        SharedPreferences.Editor editor = settings.edit();
        value= rank.getText().toString();
        editor.putString("value", value);
        editor.commit();
    }
    public static int calculatePreviewOrientation(Camera.CameraInfo info, int rotation) {
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }
}
