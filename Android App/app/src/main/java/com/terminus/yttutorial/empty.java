package com.terminus.yttutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

class empty extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        intent=getIntent();
        intent = new Intent(empty.this, version1.class);
        startActivity(intent);
        finish();
    }
}
