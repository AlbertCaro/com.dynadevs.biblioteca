package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dynadevs.classes.User;
import com.dynadevs.fragments.BookmarksFragment;
import com.dynadevs.fragments.BooksFragment;
import com.dynadevs.fragments.FinesFragment;
import com.dynadevs.fragments.LoansFragment;
import com.dynadevs.fragments.MainFragment;
import com.dynadevs.interfaces.FragmentsListenerInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentsListenerInterface {

    private Bundle bundle;
    private Fragment fragment;
    private TextView TvAppTitle, TvName, TvCareer, TvUniversity;
    private ImageView IvDrawerHeader;
    private SearchView searchView;
    private Intent intent;
    private FloatingActionButton fab;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        bundle = getIntent().getExtras();
        TvAppTitle = (TextView) findViewById(R.id.tvAppTitle);
        TvAppTitle.setText(R.string.nav_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        User user = (User) (bundle != null ? bundle.getSerializable("user") : null);
        IvDrawerHeader = navView.findViewById(R.id.ivUniversity);
        Glide.with(this).load(getString(R.string.server_url)+"biblioteca/images/biblios/"+(user != null ? user.getDrawerHeader() : null)).into(IvDrawerHeader);
        TvUniversity = navView.findViewById(R.id.tvUniversity);
        TvUniversity.setText(user != null ? user.getCareerAbrebiation() : null);
        TvName = navView.findViewById(R.id.tvNameUser);
        TvName.setText(user != null ? user.getName() : null);
        TvCareer = navView.findViewById(R.id.tvCareerUser);
        TvCareer.setText(user != null ? user.getCareer() : null);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvAppTitle.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                TvAppTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });

        fragment = new MainFragment();
        fragment.setArguments(bundle);
        actionBar.setTitle(R.string.nav_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.clContenedor, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout){
            SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean activity = false;

        if (id == R.id.nav_main) {
            TvAppTitle.setText(R.string.nav_main);
            actionBar.setTitle(R.string.nav_main);
            fragment = new MainFragment();
            fab.hide();
        } else if(id == R.id.nav_books) {
            TvAppTitle.setText(R.string.nav_books);
            actionBar.setTitle(R.string.nav_books);
            fragment = new BooksFragment();
        } else if (id == R.id.nav_bookmarks) {
            TvAppTitle.setText(R.string.nav_bookmarks);
            actionBar.setTitle(R.string.nav_bookmarks);
            fragment = new BookmarksFragment();
        } else if (id == R.id.nav_loans) {
            TvAppTitle.setText(R.string.nav_loans);
            actionBar.setTitle(R.string.nav_loans);
            fragment = new LoansFragment();
        } else if (id == R.id.nav_fines) {
            TvAppTitle.setText(R.string.nav_fines);
            actionBar.setTitle(R.string.nav_fines);
            fragment = new FinesFragment();
        } else if (id == R.id.nav_help) {
            intent = new Intent(MainActivity.this, HelpActivity.class);
            activity = true;
        } else if (id == R.id.nav_about) {
            intent = new Intent(MainActivity.this, AboutActivity.class);
            activity = true;
        }

        if (activity)
            startActivity(intent);
        else {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.clContenedor, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
