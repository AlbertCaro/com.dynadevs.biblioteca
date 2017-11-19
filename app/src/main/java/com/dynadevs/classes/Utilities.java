package com.dynadevs.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dynadevs.activities.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Alberto Caro Navarro on 02/11/2017.
 * albertcaronava@gmail.com
 */

public abstract class Utilities {
    public static boolean isNetAvailible(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (connectivityManager != null)
            info = connectivityManager.getActiveNetworkInfo();
        return (info != null && ( info.isConnected() && info.isAvailable() && info.isConnectedOrConnecting()));
    }

    public static void setMessage(String Message, TextView textView, LinearLayout layout, RecyclerView recyclerView) {
        textView.setText(Message);
        layout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public static String md5(String string) {
        MessageDigest MD = null;
        try {
            MD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (MD != null)
            MD.update(string.getBytes(),0, string.length());
        return new BigInteger(1, MD != null ? MD.digest() : new byte[0]).toString(16);
    }

    public static void setCurrentThemeActivity (Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                activity.setTheme(R.style.AppTheme);
                break;
            case 1:
                activity.setTheme(R.style.RedTheme);
                break;
            case 2:
                activity.setTheme(R.style.PurpleTheme);
                break;
            case 3:
                activity.setTheme(R.style.GreenTheme);
                break;
            case 4:
                activity.setTheme(R.style.AndroidTheme);
                break;
        }
    }

    public static SharedPreferences setCurrentTheme(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                activity.setTheme(R.style.AppTheme_NoActionBar);
                break;
            case 1:
                activity.setTheme(R.style.RedTheme_NoActionBar);
                break;
            case 2:
                activity.setTheme(R.style.PurpleTheme_NoActionBar);
                break;
            case 3:
                activity.setTheme(R.style.GreenTheme_NoActionBar);
                break;
            case 4:
                activity.setTheme(R.style.AndroidTheme_NoActionBar);
                break;
        }
        return preferences;
    }

    public static User loadSesion (Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        if (preferences.contains("code")) {
            return new User(
                    preferences.getString("code",""),
                    preferences.getString("name",""),
                    preferences.getString("email",""),
                    preferences.getString("university",""),
                    preferences.getString("career",""),
                    preferences.getString("acronym",""),
                    preferences.getString("image","")
            );
        } else {
            return null;
        }
    }

    public static boolean verifyLoadedSesion(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.contains("code");
    }

    public static boolean getEventSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("event", true);
    }



    public static void registreEventPreference(Loan loan, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(loan.getISBN(), "true");
        editor.apply();
    }

    public static int getNotificationTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getInt("notification_time", 5);
    }

    public static boolean findEvent(Loan loan, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        return preferences.contains(loan.getISBN());
    }
}
