package com.example.medminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = new Intent(this, ScreenListenerService.class);
        startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this,MainActivity.class));

            }
        }).start();
    }
}