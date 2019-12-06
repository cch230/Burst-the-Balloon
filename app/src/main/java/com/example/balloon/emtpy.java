package com.example.balloon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class emtpy extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emtpy);
        intent=getIntent();
        intent = new Intent(emtpy.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
