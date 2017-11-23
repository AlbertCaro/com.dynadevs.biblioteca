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
    public static int setCurrentThemeActivity (Activity activity) {
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
        return preferences.getInt("theme", 0);
    }

    public static void setCurrentThemeActivity (Activity activity, User user) {
        int theme = setCurrentThemeActivity(activity);
        if (theme == 0) {
            if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias exactas e ingenierias"))
                activity.setTheme(R.style.CUCEI);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de arte arquitectura y diseño"))
                activity.setTheme(R.style.CUAAD);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de los altos"))
                activity.setTheme(R.style.CUAltos);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias"))
                activity.setTheme(R.style.CUCBA);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias economico administrativas"))
                activity.setTheme(R.style.CUCEA);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la cienega"))
                activity.setTheme(R.style.CUCienega);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa"))
                activity.setTheme(R.style.CUCosta);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias de la salud"))
                activity.setTheme(R.style.CUCS);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias sociales y humanidades"))
                activity.setTheme(R.style.CUCSH);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa sur"))
                activity.setTheme(R.style.CUCSur);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de los lagos"))
                activity.setTheme(R.style.CULagos);
            else if (user.getUniversity().toLowerCase().equals("centro universitario del norte"))
                activity.setTheme(R.style.CUNorte);
            else if (user.getUniversity().toLowerCase().equals("centro universitario del sur"))
                activity.setTheme(R.style.CUSur);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de tonala"))
                activity.setTheme(R.style.CUTonala);
        }
    }

    /**
     * Lo mismo que el anterior, sólo que este está destinado a activitys que no tienen action bar.
     * @param activity Requiere de la activity a la que se le aplicará.
     */
    public static int setCurrentTheme(Activity activity) {
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
        return preferences.getInt("theme", 0);
    }

    public static void setCurrentTheme(Activity activity, User user) {
        int theme = setCurrentTheme(activity);
        if (theme == 0) {
            if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias exactas e ingenierias"))
                activity.setTheme(R.style.CUCEI_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de arte arquitectura y diseño"))
                activity.setTheme(R.style.CUAAD_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de los altos"))
                activity.setTheme(R.style.CUAltos_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias"))
                activity.setTheme(R.style.CUCBA_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias economico administrativas"))
                activity.setTheme(R.style.CUCEA_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la cienega"))
                activity.setTheme(R.style.CUCienega_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa"))
                activity.setTheme(R.style.CUCosta_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias de la salud"))
                activity.setTheme(R.style.CUCS_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias sociales y humanidades"))
                activity.setTheme(R.style.CUCSH_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa sur"))
                activity.setTheme(R.style.CUCSur_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de los lagos"))
                activity.setTheme(R.style.CULagos_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario del norte"))
                activity.setTheme(R.style.CUNorte_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario del sur"))
                activity.setTheme(R.style.CUSur_NoActionBar);
            else if (user.getUniversity().toLowerCase().equals("centro universitario de tonala"))
                activity.setTheme(R.style.CUTonala_NoActionBar);
        }
    }

    /**
     * Coloca el accent dependiendo del tema a los SwipeRefreshLayouts de los fragments
     * @param swipeRefreshLayout Obviamente el layout al que se le aplicará el tema
     * @param activity Actividad de la que se obtendrá el método getSharedPreferences
     */
    public static void setCurrentAccent(SwipeRefreshLayout swipeRefreshLayout, Activity activity, User user) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias exactas e ingenierias"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCEI);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de arte arquitectura y diseño"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUAAD);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de los altos"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUAltos);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCBA);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias economico administrativas"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCEA);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la cienega"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCienega);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCosta);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias de la salud"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCS);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias sociales y humanidades"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCSH);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa sur"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUCSur);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de los lagos"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CULagos);
                else if (user.getUniversity().toLowerCase().equals("centro universitario del norte"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUNorte);
                else if (user.getUniversity().toLowerCase().equals("centro universitario del sur"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUSur);
                else if (user.getUniversity().toLowerCase().equals("centro universitario de tonala"))
                    swipeRefreshLayout.setColorSchemeResources(R.color.CUTonala);
                else
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
            String Image;
            String University = preferences.getString("university","");
            if (University.toLowerCase().equals("centro universitario de ciencias exactas e ingenierias"))
                Image = "2.jpg";
            else if (University.toLowerCase().equals("centro universitario de arte arquitectura y diseño"))
                Image = "3.jpg";
            else if (University.toLowerCase().equals("centro universitario de los altos"))
                Image = "4.jpg";
            else if (University.toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias"))
                Image = "5.jpg";
            else if (University.toLowerCase().equals("centro universitario de ciencias economico administrativas"))
                Image = "6.jpg";
            else if (University.toLowerCase().equals("centro universitario de la cienega"))
                Image = "7.jpg";
            else if (University.toLowerCase().equals("centro universitario de la costa"))
                Image = "8.jpg";
            else if (University.toLowerCase().equals("centro universitario de ciencias de la salud"))
                Image = "9.jpg";
            else if (University.toLowerCase().equals("centro universitario de ciencias sociales y humanidades"))
                Image = "10.jpg";
            else if (University.toLowerCase().equals("centro universitario de la costa sur"))
                Image = "11.jpg";
            else if (University.toLowerCase().equals("centro universitario de los lagos"))
                Image = "12.jpg";
            else if (University.toLowerCase().equals("centro universitario del norte"))
                Image = "13.jpg";
            else if (University.toLowerCase().equals("centro universitario del sur"))
                Image = "14.jpg";
            else if (University.toLowerCase().equals("centro universitario de tonala"))
                Image = "15.jpg";
            else
                Image = "1.png";
            return new User(
                    preferences.getString("code",""),
                    preferences.getString("name",""),
                    preferences.getString("email",""),
                    University,
                    preferences.getString("career",""),
                    preferences.getString("acronym",""),
                    Image
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
