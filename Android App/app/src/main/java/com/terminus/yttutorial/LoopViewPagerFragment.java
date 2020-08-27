package com.terminus.yttutorial;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.terminus.yttutorial.Loop.LoopViewPager;

import com.terminus.yttutorial.circleindicator.CircleIndicator;
import com.terminus.yttutorial.library.Titanic;
import com.terminus.yttutorial.library.TitanicTextView;

import static android.widget.Toast.makeText;


public class LoopViewPagerFragment extends Fragment implements SampleActivity.OnBackPressedListener {
    public ImageView imageView;
    private long backKeyPressedTime = 0;
    SampleActivity activity;
    Intent intent;
    Toast toast;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = (SampleActivity) getActivity();
        return inflater.inflate(R.layout.activity_loop_view_pager_fragment, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LoopViewPager viewpager = view.findViewById(R.id.viewpager);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        viewpager.setAdapter(new SamplePagerAdapter());
        indicator.setViewPager(viewpager);
    }
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            intent = new Intent(this.getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override public void onResume(){
        super.onResume();
        activity.setOnBackPressedListener(this);

    }
}
