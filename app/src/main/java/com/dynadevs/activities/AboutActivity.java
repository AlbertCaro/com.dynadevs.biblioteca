package com.dynadevs.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.dynadevs.adapters.AboutAdapter;
import com.dynadevs.classes.Person;

import java.util.ArrayList;

import static com.dynadevs.classes.Utilities.setCurrentThemeActivity;

public class AboutActivity extends AppCompatActivity {
    private ArrayList<Person> PersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentThemeActivity(this);
        setContentView(R.layout.activity_about);
        TextView TvVersion = findViewById(R.id.about_version);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            TvVersion.setText(getString(R.string.about_version)+" "+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_about);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PersonList.add(new Person("Alberto Caro Navarro", "Tecnologías de la Información", "alberto.cnavarro@alumnos.udg.mx", R.drawable.alberto));
        PersonList.add(new Person("Jonathan Iván Pérez Uribe", "Tecnologías de la Información", "jonathanperez@alumnos.udg.mx", R.drawable.jonathan));
        PersonList.add(new Person("Luis Ángel García Castro", "Tecnologías de la Información", "luisgarcia@alumnos.udg.mx", R.drawable.luis));

        AboutAdapter adapter = new AboutAdapter(PersonList);
        RecyclerView recyclerView = findViewById(R.id.rvAbout);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
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
