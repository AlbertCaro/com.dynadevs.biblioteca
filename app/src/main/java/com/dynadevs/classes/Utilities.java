package com.dynadevs.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
    /**
     * Función que verifica si se está conectado a WiFi o datos.
     * @param activity De esta activity usa los métodos como getSystemService
     * @return Retorna un boleano que determina si hay o no datos o WiFi
     */
    public static boolean isNetAvailible(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (connectivityManager != null)
            info = connectivityManager.getActiveNetworkInfo();
        return (info != null && ( info.isConnected() && info.isAvailable() && info.isConnectedOrConnecting()));
    }

    /**
     * Función que establece el mensaje en el LinearLayout cuando no hay conexión o la lista está
     * vacía, además que hace visible el layout.
     * @param Message Mensaje a mostrar en el TextView
     * @param textView Componente al que se le añade el mensaje
     * @param layout Contenedor en el que se ubica el mensaje y su imagen
     * @param recyclerView La lista a ocultar
     */
    public static void setMessage(String Message, TextView textView, LinearLayout layout, RecyclerView recyclerView) {
        textView.setText(Message);
        layout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * Función para encriptar cadena a MD5
     * @param string Cadena a ser encriptada.
     * @return Retorna la cadena encriptada.
     */
    public static String md5(String string) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (messageDigest != null)
            messageDigest.update(string.getBytes(),0, string.length());
        return new BigInteger(1, messageDigest != null ? messageDigest.digest() : new byte[0]).toString(16);
    }

    /**
     * Coloca el tema guardado en los SharedPreferences dependiendo del entero que estos contengan.
     * @param activity Requiere de la activity a la que se le aplicará.
     */
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

    /**
     * Lo mismo que el anterior, sólo que este está destinado a activitys que no tienen action bar.
     * @param activity Requiere de la activity a la que se le aplicará.
     */
    public static void setCurrentTheme(Activity activity) {
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
    }

    /**
     * Coloca el accent dependiendo del tema a los SwipeRefreshLayouts de los fragments
     * @param swipeRefreshLayout Obviamente el layout al que se le aplicará el tema
     * @param activity Actividad de la que se obtendrá el método getSharedPreferences
     */
    public static void setCurrentAccent(SwipeRefreshLayout swipeRefreshLayout, Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
                break;
            default:
                swipeRefreshLayout.setColorSchemeResources(R.color.colorAccentAndroid);
        }
    }

    /**
     * Método que determina la existencia de una sesión guardada en los SharedPreferences.
     * @param activity Actividad de la que se obtiene el método getSharedPreferences
     * @return Retorna un objeto tipo usuario.
     */
    @Nullable
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

    /**
     * Obtiene el boleano que determina si el usuario quiere, o no, agregar los eventos de sus
     * prestamos al calendario.
     * @param context Requiere un contexto para obtener el método getSharedPreferences.
     * @return Retorna el boleano antes mencionado.
     */
    public static boolean getEventSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("event", true);
    }

    /**
     * Método que guarda en SharedPreferences un boleano que determina si ya se agregó o no el evento
     * de cada libro al calendario.
     * @param loan La información del prestamo del libro.
     * @param context Contexto para obtener el método getSharedPreferences.
     */
    public static void registreEventPreference(Loan loan, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(loan.getISBN(), true);
        editor.apply();
    }

    /**
     * Obtiene el tiempo de la notificación determinada por el usuario.
     * @param context Contexto del cual obtiene el método getSharedPreferences.
     * @return Retorna el entero del tiempo.
     */
    public static int getNotificationTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getInt("notification_time", 5);
    }

    /**
     * Método del cual se obtiene el boleano guardado en {@link #registreEventPreference(Loan, Context)}
     * para determinar si ya existe o no el evento en el calendario.
     * @param loan Información del prestamo
     * @param context Contexto del cual se obtiene getSharedPreferences
     * @return retorna el boleano.
     */
    public static boolean findEvent(Loan loan, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        return preferences.getBoolean(loan.getISBN(), false);
    }
}
