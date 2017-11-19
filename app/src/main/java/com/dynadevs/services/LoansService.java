package com.dynadevs.services;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.activities.R;
import com.dynadevs.classes.Loan;
import com.dynadevs.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import static com.dynadevs.classes.Utilities.findEvent;
import static com.dynadevs.classes.Utilities.getEventSettings;
import static com.dynadevs.classes.Utilities.getNotificationTime;
import static com.dynadevs.classes.Utilities.md5;
import static com.dynadevs.classes.Utilities.registreEventPreference;

/**
 * Created by Alberto Caro Navarro on 18/11/2017.
 */

public class LoansService extends Service {
    private ArrayList<Loan> LoanList = new ArrayList<>();
    private String Code;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("user");
        Code = user.getCode();

        if (getEventSettings(this)) {
            doRequestLoans();
            for (int i = 0; i < LoanList.size(); i++) {
                if (!findEvent(LoanList.get(i), this))
                    addRecordatory(LoanList.get(i));
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void doRequestLoans() {
        LoanList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(LoansService.this);
        String Url = getString(R.string.server_url)+"biblioteca/rest/prestamos.php?id="+md5(Code);
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            LoanList.add(new Loan(
                                    jsonObject.getString("Titulo"),
                                    jsonObject.getString("ISBN"),
                                    Integer.parseInt(jsonObject.getString("Dia")),
                                    Integer.parseInt(jsonObject.getString("Mes")),
                                    Integer.parseInt(jsonObject.getString("Año"))));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 800;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }

    private void addRecordatory(Loan loan) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(loan.getYear(), loan.getMounth()-1, loan.getDay(), 8, 0);
            long startMillis = beginTime.getTimeInMillis();
            Calendar endTime = Calendar.getInstance();
            endTime.set(loan.getYear(), loan.getMounth()-1, loan.getDay(), 18, 0);
            long endMillis = endTime.getTimeInMillis();
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, loan.getTitle());
            values.put(CalendarContract.Events.DESCRIPTION, getString(R.string.event_message));
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Mexico_city");
            try {
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                try {
                    long eventID = Long.parseLong(uri != null ? uri.getLastPathSegment() : null);
                    ContentValues valuesRemind = new ContentValues();
                    valuesRemind.put(CalendarContract.Reminders.MINUTES, getNotificationTime(this));
                    valuesRemind.put(CalendarContract.Reminders.EVENT_ID, eventID);
                    valuesRemind.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    cr.insert(CalendarContract.Reminders.CONTENT_URI, valuesRemind);
                    registreEventPreference(loan, this);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
