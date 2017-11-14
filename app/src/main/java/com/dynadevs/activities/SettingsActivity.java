package com.dynadevs.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import static com.dynadevs.classes.Utilities.setCurrentTheme;
import static com.dynadevs.classes.Utilities.setCurrentThemeActivity;

public class SettingsActivity extends AppCompatActivity {
    private ListView LvSettings;
    private List<String[]> ListOptions = new LinkedList<>();
    private int currentTheme;
    private boolean currentEventOption;
    private int currentEventOptionNum;
    private int currentTime;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentThemeActivity(this);
        setContentView(R.layout.activity_settings);
        bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(getString(R.string.settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LvSettings = findViewById(R.id.lvSettings);
        ListOptions.add(new String[]{getString(R.string.settings_theme), getString(R.string.settings_theme_sub)});
        ListOptions.add(new String[]{getString(R.string.settings_events), getString(R.string.settings_events_sub)});
        ListOptions.add(new String[]{getString(R.string.settings_notification_time), getString(R.string.settings_notification_time_sub)});
        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, ListOptions){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String[] entry = ListOptions.get(position);
                TextView title = view.findViewById(android.R.id.text1);
                TextView comment = view.findViewById(android.R.id.text2);
                title.setText(entry[0]);
                comment.setText(entry[1]);
                title.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                comment.setTextColor(getResources().getColor(R.color.colorSecoundaryText));
                return view;
            }
        };

        LvSettings.setAdapter(adapter);
        LvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    showThemeDialog();
                else if (position == 1)
                    showEventDialog();
                else if (position == 2)
                    showTimeDialog();
            }
        });
    }

    public void showThemeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings_theme);
        searchCurrentSettings();

        builder.setSingleChoiceItems(R.array.theme_options, currentTheme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveCurrentTheme(which);
                if (which != currentTheme) {
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        builder.show();
    }

    public void showEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings_events);
        searchCurrentSettings();

        builder.setSingleChoiceItems(R.array.notification_options, currentEventOptionNum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    saveCurrentEventOption(true);
                else
                    saveCurrentEventOption(false);
            }
        });
        builder.show();
    }

    public void showTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings_notification_time);
        searchCurrentSettings();

        builder.setSingleChoiceItems(R.array.time_options, currentTime, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    saveTime(5);
                else if (which == 1)
                    saveTime(30);
                else if (which == 2)
                    saveTime(720);
                else if (which == 3)
                    saveTime(1440);
            }
        });
        builder.show();
    }

    private void searchCurrentSettings() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (preferences.contains("theme")) {
            currentTheme = preferences.getInt("theme", 0);
            currentEventOption = preferences.getBoolean("event", true);
            if (currentEventOption)
                currentEventOptionNum = 0;
            else
                currentEventOptionNum = 1;
            switch (preferences.getInt("notification_time", 15)) {
                case 5:
                    currentTime = 0;
                    break;
                case 30:
                    currentTime = 1;
                    break;
                case 720:
                    currentTime = 2;
                    break;
                case 1440:
                    currentTime = 3;
                    break;
            }

        }
    }

    private void saveCurrentTheme(int theme) {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("theme", theme);
        editor.apply();
    }

    private void saveCurrentEventOption(boolean option) {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("event", option);
        editor.apply();
    }

    private void saveTime(int time) {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("notification_time", time);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
