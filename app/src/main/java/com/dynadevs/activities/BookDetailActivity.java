package com.dynadevs.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.classes.Book;
import com.dynadevs.fragments.BookDetailContentFragment;
import com.bumptech.glide.Glide;


public class BookDetailActivity extends AppCompatActivity implements BookDetailContentFragment.OnFragmentInteractionListener {
    ImageView IvPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();

        BookDetailContentFragment bookDetail = new BookDetailContentFragment();
        IvPhoto = (ImageView) findViewById(R.id.ivPhoto);
        Book book = (Book) bundle.getSerializable("book");
        actionBar.setTitle(book.getTitle());
        Glide.with(this).load(book.getPhoto(BookDetailActivity.this)).centerCrop().into(IvPhoto);
        bookDetail.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.book_detail_container, bookDetail).addToBackStack(null).commit();
    }

    private void doRequest(String Id, String ISBN) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url)+"biblioteca/rest/add_marcadores.php?id="+Id+"&isbn="+ISBN;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //navigateUpTo(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
