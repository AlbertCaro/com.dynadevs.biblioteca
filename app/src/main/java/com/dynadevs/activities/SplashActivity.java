package com.dynadevs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by beto_ on 28/09/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
