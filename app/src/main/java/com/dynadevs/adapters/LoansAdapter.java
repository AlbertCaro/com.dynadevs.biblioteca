package com.dynadevs.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynadevs.activities.R;
import com.dynadevs.classes.Loan;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.ViewHolderLoans> {
    private ArrayList<Loan> LoanList;
    private Context context;
    private Activity activity;

    public LoansAdapter(ArrayList<Loan> loanList, Context context, Activity activity) {
        LoanList = loanList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public LoansAdapter.ViewHolderLoans onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_loans, parent, false);
        return new ViewHolderLoans(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(LoansAdapter.ViewHolderLoans holder, final int position) {
        holder.TvTitle.setText(LoanList.get(position).getTitle());
        holder.TvDate.setText(LoanList.get(position).getDay()+"/"+LoanList.get(position).getMounth()+"/"+LoanList.get(position).getYear());
        if (!findEvent(LoanList.get(position))) {
            if (getEventSettings())
                addRecordatory(LoanList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return LoanList.size();
    }

    public void setLoanList(ArrayList<Loan> loanList) {
        LoanList = loanList;
    }

    class ViewHolderLoans extends RecyclerView.ViewHolder {
        TextView TvTitle, TvDate;

        ViewHolderLoans(View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.tvTitleLoan);
            TvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    private void addRecordatory(Loan loan) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
        } else {
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(loan.getYear(), loan.getMounth()-1, loan.getDay(), 8, 0);
            long startMillis = beginTime.getTimeInMillis();
            Calendar endTime = Calendar.getInstance();
            endTime.set(loan.getYear(), loan.getMounth()-1, loan.getDay(), 18, 0);
            long endMillis = endTime.getTimeInMillis();
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, loan.getTitle());
            values.put(CalendarContract.Events.DESCRIPTION, activity.getString(R.string.event_message));
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Mexico_city");
            try {
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                try {
                    long eventID = Long.parseLong(uri != null ? uri.getLastPathSegment() : null);
                    ContentValues valuesRemind = new ContentValues();
                    valuesRemind.put(CalendarContract.Reminders.MINUTES, getNotificationTime());
                    valuesRemind.put(CalendarContract.Reminders.EVENT_ID, eventID);
                    valuesRemind.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    cr.insert(CalendarContract.Reminders.CONTENT_URI, valuesRemind);
                    registreEventPreference(loan);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean findEvent(Loan loan) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        return preferences.contains(loan.getISBN());
    }

    private void registreEventPreference(Loan loan) {
        SharedPreferences preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(loan.getISBN(), "true");
        editor.apply();
    }

    private boolean getEventSettings() {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("event", true);
    }

    private int getNotificationTime() {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getInt("notification_time", 5);
    }
}
