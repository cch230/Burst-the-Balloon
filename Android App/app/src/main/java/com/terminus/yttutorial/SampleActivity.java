package com.terminus.yttutorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.terminus.yttutorial.library.Titanic;
import com.terminus.yttutorial.library.TitanicTextView;

import static android.widget.Toast.makeText;

public class SampleActivity extends AppCompatActivity {
    OnBackPressedListener listener;
    private long backKeyPressedTime = 0;
    private Toast toast;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoopViewPagerFragment())
                    .commit();
        }
    }
    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener=listener;
    }
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if(listener!=null){
            listener.onBackPressed();
        }
        else{
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
    public interface OnBackPressedListener { void onBackPressed(); }

}
