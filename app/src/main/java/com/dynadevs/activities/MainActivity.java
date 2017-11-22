package com.dynadevs.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dynadevs.classes.User;
import com.dynadevs.fragments.AudiobooksFragment;
import com.dynadevs.fragments.BookmarksFragment;
import com.dynadevs.fragments.BooksFragment;
import com.dynadevs.fragments.FinesFragment;
import com.dynadevs.fragments.LoansFragment;
import com.dynadevs.fragments.MainFragment;

import static com.dynadevs.classes.Utilities.isNetAvailible;
import static com.dynadevs.classes.Utilities.loadSesion;
import static com.dynadevs.classes.Utilities.setCurrentTheme;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        BooksFragment.OnFragmentInteractionListener,
        BookmarksFragment.OnFragmentInteractionListener,
        LoansFragment.OnFragmentInteractionListener,
        FinesFragment.OnFragmentInteractionListener,
        AudiobooksFragment.OnFragmentInteractionListener{

    private Bundle bundle;
    private Fragment fragment;
    private TextView TvAppTitle;
    private Intent intent;
    private FloatingActionButton fab;
    private ActionBar actionBar;
    private User user;
    private Menu menu;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            user = (User) bundle.getSerializable("user");
            setCurrentTheme(this, user);
        } else
            setCurrentTheme(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        TvAppTitle = findViewById(R.id.tvAppTitle);
        TvAppTitle.setText(R.string.nav_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView ivDrawerHeader = navView.findViewById(R.id.ivUniversity);
        TextView tvUniversity = navView.findViewById(R.id.tvUniversity);
        TextView tvName = navView.findViewById(R.id.tvNameUser);
        TextView tvCareer = navView.findViewById(R.id.tvCareerUser);
        SearchView searchView = findViewById(R.id.search);
        menu = navigationView.getMenu();
        menu.findItem(R.id.nav_main).setChecked(true);

        if (getIntent().getExtras() != null) {
            Glide.with(this).load(getString(R.string.server_url)+"biblioteca/images/biblios/"+
                    (user != null ? user.getDrawerHeader() : loadSesion(this).getDrawerHeader())).fitCenter().into(ivDrawerHeader);
            /*
            if (getEventSettings(this)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
                } else {
                    Intent intent = new Intent(this, LoansService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                }
            }*/
            MenuItem itemLogin = menu.findItem(R.id.nav_login);
            itemLogin.setIcon(R.drawable.ic_logout);
            itemLogin.setTitle(R.string.logout);
        } else {
            menu.findItem(R.id.nav_bookmarks).setVisible(false);
            menu.findItem(R.id.nav_loans).setVisible(false);
            menu.findItem(R.id.nav_fines).setVisible(false);
            menu.findItem(R.id.nav_help).setVisible(false);
            navView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        tvUniversity.setText(user != null ? user.getAcronym() : getString(R.string.udg));
        tvName.setText(user != null ? user.getName() : getString(R.string.login));
        tvCareer.setText(user != null ? user.getCareer() : getString(R.string.not_logged));

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.out)).setMessage(getString(R.string.out_message))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        } else
            super.onBackPressed();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.clContenedor);
        if (fragment instanceof MainFragment) {
            TvAppTitle.setText(R.string.nav_main);
            actionBar.setTitle(R.string.nav_main);
            menu.findItem(R.id.nav_main).setChecked(true);
            fab.hide();
        } else if (fragment instanceof BooksFragment) {
            TvAppTitle.setText(R.string.nav_books);
            actionBar.setTitle(R.string.nav_books);
            menu.findItem(R.id.nav_books).setChecked(true);
            fab.show();
        } else if (fragment instanceof FinesFragment) {
            TvAppTitle.setText(R.string.nav_fines);
            actionBar.setTitle(R.string.nav_fines);
            menu.findItem(R.id.nav_fines).setChecked(true);
            fab.show();
        } else if (fragment instanceof LoansFragment) {
            TvAppTitle.setText(R.string.nav_loans);
            actionBar.setTitle(R.string.nav_loans);
            menu.findItem(R.id.nav_loans).setChecked(true);
            fab.show();
        } else if (fragment instanceof BookmarksFragment) {
            TvAppTitle.setText(R.string.nav_bookmarks);
            actionBar.setTitle(R.string.nav_bookmarks);
            menu.findItem(R.id.nav_bookmarks).setChecked(true);
            fab.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            intent = new Intent(MainActivity.this, SettingsActivity.class);
            if (getIntent().getExtras() != null)
                intent.putExtras(bundle);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        boolean activity = false;
        boolean change = true;

        if (id == R.id.nav_main) {
            TvAppTitle.setText(R.string.nav_main);
            actionBar.setTitle(R.string.nav_main);
            TvAppTitle.setVisibility(View.VISIBLE);
            fragment = new MainFragment();
            fab.hide();
        } else if(id == R.id.nav_books) {
            TvAppTitle.setText(R.string.nav_books);
            actionBar.setTitle(R.string.nav_books);
            fragment = new BooksFragment();
        }/* else if(id == R.id.nav_audiobooks) {
            TvAppTitle.setText(R.string.nav_audiobooks);
            actionBar.setTitle(R.string.nav_audiobooks);
            fragment = new AudiobooksFragment();
        }*/ else if (id == R.id.nav_bookmarks) {
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
        } else if (id == R.id.nav_login) {
            activity = true;
            if (getIntent().getExtras() != null) {
                change = false;
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.logout_message)).setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                preferences.edit().clear().apply();
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton(getString(R.string.no), null).show();
            } else
                intent = new Intent(MainActivity.this, LoginActivity.class);
        } else if (id == R.id.nav_help) {
            if (isNetAvailible(this)) {
                intent = new Intent(MainActivity.this, HelpActivity.class);
                intent.putExtras(bundle);
                activity = true;
            } else {
                Toast.makeText(this, getString(R.string.need_internet), Toast.LENGTH_SHORT).show();
                change = false;
            }
        } else if (id == R.id.nav_about) {
            intent = new Intent(MainActivity.this, AboutActivity.class);
            activity = true;
        }

        if (change) {
            if (activity) {
                startActivity(intent);
            } else {
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.clContenedor, fragment);
                transaction.addToBackStack("screen").commit();
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
