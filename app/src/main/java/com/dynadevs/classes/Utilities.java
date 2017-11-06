package com.dynadevs.classes;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by beto_ on 02/11/2017.
 */

public abstract class Utilities {
    public static boolean isNetAvailible(Activity activity, Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && ( actNetInfo.isConnected() && actNetInfo.isAvailable()
                && actNetInfo.isConnectedOrConnecting()));
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
        if (MD != null) {
            MD.update(string.getBytes(),0, string.length());
        }
        return new BigInteger(1, MD != null ? MD.digest() : new byte[0]).toString(16);
    }
}
