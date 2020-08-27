package com.terminus.yttutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.terminus.yttutorial.library.Titanic;
import com.terminus.yttutorial.library.TitanicTextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class SamplePagerAdapter extends PagerAdapter {
    public static final String RECODE1= "recode rank1";
    public static final String RECODE2= "recode rank2";

    private final Random random = new Random();
    private int mSize;
    private ArrayList<String> rankList1= new ArrayList<>();
    private ArrayList<String> rankList2= new ArrayList<>();
    public SamplePagerAdapter() {
        mSize = 5;
    }

    @Override public int getCount() {
        return mSize;
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup view, int position, @NonNull Object object) {
        view.removeView((View) object);
    }

    @NonNull @Override public Object instantiateItem(@NonNull ViewGroup view, int position) {
        for(int i=0; i<mSize;i++){
            rankList1.add(String.valueOf(position + 1));
            rankList2.add(String.valueOf(position + 1));
        }
        rankList1=get_rank1(view.getContext());
        rankList2=get_rank2(view.getContext());

        TitanicTextView textView =  new TitanicTextView(view.getContext());
        textView.setText(String.valueOf(position + 1)+" 등\n"+ "Original mode : " +rankList1.get(position)+
                "\n AR mode : "+rankList2.get(position));
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0xff212121);
        textView.setTextSize(48);
        textView.setTypeface(com.romainpiel.titanic.sample.Typefaces.get(view.getContext(), "Satisfy-Regular.ttf"));
        // start animation
        new Titanic().start(textView);
        view.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;
    }
    ArrayList<String> get_rank1(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(RECODE1, 0);
        Map<String,String> prefrences= (Map<String, String>) prefs.getAll();
        ArrayList<String> arrayList=new ArrayList<>();
        Iterator<String> iterator = prefrences.keySet().iterator();
        int i=0;
        Float temp=0.0f;
        Float[] grade_array=new Float[prefrences.size()];

        while (iterator.hasNext()){
            String str_time=(String) iterator.next();
            String grade=prefrences.get(str_time);
            grade_array[i]=Float.parseFloat(grade);
            i++;
        }
        for ( i = 0; i < grade_array.length; i++) {
            for (int j = i+1; j < grade_array.length; j++) {
                if(grade_array[i] > grade_array[j]) {
                    temp = grade_array[i];
                    grade_array[i] = grade_array[j];
                    grade_array[j] = temp;
                }
            }
        }
        for (i=0;i<5;i++){

            arrayList.add(String.format("%.3f", (grade_array[i])));
        }
        return arrayList;
    }

    ArrayList<String> get_rank2(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(RECODE2, 0);
        Map<String,String> prefrences= (Map<String, String>) prefs.getAll();
        ArrayList<String> arrayList=new ArrayList<>();
        Iterator<String> iterator = prefrences.keySet().iterator();
        int i=0,temp;
        int[] grade_array=new int[prefrences.size()];

        while (iterator.hasNext()){
            String str_time=(String) iterator.next();
            String grade=prefrences.get(str_time);
            grade_array[i]=Integer.parseInt(grade);
            i++;
        }
        for (i = 0; i < grade_array.length; i++) {
            for (int j = i+1; j < grade_array.length; j++) {
                if(grade_array[i] < grade_array[j]) {
                    temp = grade_array[i];
                    grade_array[i] = grade_array[j];
                    grade_array[j] = temp;
                }
            }
        }

        for (i=0;i<5;i++){

            arrayList.add(String.valueOf(grade_array[i]));
        }
        return arrayList;
    }
}