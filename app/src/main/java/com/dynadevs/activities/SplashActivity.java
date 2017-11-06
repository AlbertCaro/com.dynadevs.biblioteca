package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dynadevs.classes.User;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by beto_ on 28/09/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private String Code, Name, Email, University, Career, Acronym, Image;
    private Intent mainIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (loadSesion()) {
            mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", new User(Code, Name, Email, University, Career, Acronym, Image));
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
        if (preferences.contains("code")) {
            Code = preferences.getString("code","");
            Name = preferences.getString("name","");
            Email = preferences.getString("email","");
            University = preferences.getString("university","");
            Career = preferences.getString("career","");
            Acronym = preferences.getString("acronym","");
            Image = preferences.getString("image","");
            return true;
        } else {
            return false;
        }
    }
}
