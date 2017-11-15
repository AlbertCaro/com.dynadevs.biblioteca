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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.classes.Book;
import com.dynadevs.classes.User;
import com.dynadevs.fragments.BookDetailContentFragment;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import static com.dynadevs.classes.Utilities.loadSesion;
import static com.dynadevs.classes.Utilities.md5;
import static com.dynadevs.classes.Utilities.setCurrentTheme;


public class BookDetailActivity extends AppCompatActivity implements BookDetailContentFragment.OnFragmentInteractionListener {
    private User user;
    private Book book;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTheme(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            book = (Book) bundle.getSerializable("book");
        user = (User) (bundle != null ? bundle.getSerializable("user") : loadSesion(this));
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        BookDetailContentFragment bookDetail = new BookDetailContentFragment();
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        fab = findViewById(R.id.fab);
        consultBookmarks();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRequest(view);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(book.getTitle());
        }
        Glide.with(this).load(book.getPhoto(BookDetailActivity.this)).centerCrop().into(ivPhoto);
        bookDetail.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.book_detail_container, bookDetail).addToBackStack(null).commit();
    }

    private void consultBookmarks() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url)+"biblioteca/rest/marcadores.php?id="+md5(user.getCode())+"&isbn="+md5(book.getISBN());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() < 1)
                        fab.setImageResource(R.drawable.ic_bookmark);
                    else
                        fab.setImageResource(R.drawable.ic_bookmark_selected);
                } catch (JSONException e) {
                    Toast.makeText(BookDetailActivity.this, "Error: "+e, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void doRequest (final View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url)+"biblioteca/rest/add_marcador.php?id="+user.getCode()+"&isbn="+book.getISBN();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (response) {
                    case "insert":
                        fab.setImageResource(R.drawable.ic_bookmark_selected);
                        Snackbar.make(view, getString(R.string.added_bookmark), Snackbar.LENGTH_SHORT).show();
                        break;
                    case "delete":
                        fab.setImageResource(R.drawable.ic_bookmark);
                        Snackbar.make(view, getString(R.string.deleted_bookmark), Snackbar.LENGTH_SHORT).show();
                        break;
                    case "failed":
                        Snackbar.make(view, getString(R.string.failed_bookmark_operation), Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(BookDetailActivity.this, getString(R.string.unexpected_response)+response, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
