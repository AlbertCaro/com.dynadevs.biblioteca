package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.dynadevs.classes.User;
import com.dynadevs.classes.Utilities;

import java.util.Timer;
import java.util.TimerTask;

import static com.dynadevs.classes.Utilities.setCurrentTheme;

/**
 * Created by Alberto Caro Navarro on 28/09/2017.
 * albertcaronava@gmail.com
 */

public class SplashActivity extends AppCompatActivity {
    private Intent mainIntent;
    private ConstraintLayout ClSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTheme(this);
        setContentView(R.layout.activity_splash);
        ClSplash = findViewById(R.id.clSplash);
        setColor(ClSplash);

        if (loadSesion()) {
            mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", Utilities.loadSesion(this));
            mainIntent.putExtras(bundle);
        } else
            mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    private boolean loadSesion () {
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.contains("code");
    }

    private void setColor(ConstraintLayout constraintLayout) {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryRed));
                break;
            case 2:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryPurple));
                break;
            case 3:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryGreen));
                break;
            case 4:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryAndroid));
                break;
        }
    }
}
