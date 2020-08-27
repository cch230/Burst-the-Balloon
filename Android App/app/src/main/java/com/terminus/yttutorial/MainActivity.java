package com.terminus.yttutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    public static final String RANK1= "my rank01";
    public static final String RANK2= "my rank02";
    private long backKeyPressedTime = 0;
    Button button01,button02;
    ImageView imageView;
    TextView textView01,textView02;
    private Intent intent;
    String value1="0",value2;
    private ImageButton rankButton;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button01= (Button) findViewById(R.id.btn1);
        button02= (Button) findViewById(R.id.btn2);
        rankButton= (ImageButton) findViewById(R.id.ranking);
        imageView= (ImageView) findViewById(R.id.newimage);
        Glide.with(this).load(R.drawable.tensorr).into(imageView);
        textView01= (TextView) findViewById(R.id.rank1);
        textView02= (TextView) findViewById(R.id.rank2);
        SharedPreferences settings01 = getSharedPreferences(RANK1, MODE_PRIVATE);
        value1= settings01.getString("value", "");
        textView01.setText(value1);
        SharedPreferences settings02 = getSharedPreferences(RANK2, MODE_PRIVATE);
        value2= settings02.getString("value", "");
        textView02.setText(value2);

    }
    public void onClick(View view){
        if(view==button01){
            intent = new Intent(MainActivity.this, version1.class);
            startActivity(intent);
            finish();
        }
        else if(view==button02){
            intent = new Intent(MainActivity.this, version2.class);
            startActivity(intent);
            finish();
        }
        else if(view==rankButton){
            intent = new Intent(MainActivity.this, SampleActivity.class);
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
            toast = makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            finish();
            toast.cancel();
            toast = makeText(this, "또 놀러오세요ㅠㅠ", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}