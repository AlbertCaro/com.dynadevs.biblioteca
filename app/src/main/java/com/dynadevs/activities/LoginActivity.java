package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dynadevs.classes.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.dynadevs.classes.Utilities.isNetAvailible;
import static com.dynadevs.classes.Utilities.md5;
import static com.dynadevs.classes.Utilities.setCurrentTheme;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout TiUser, TiPass;
    private EditText EtUser, EtPass;
    private User user;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private InputMethodManager methodManager;

    private String Code, Name, Email, University, Career, Acronym, Image, Pass;
    protected String Url;
    private int accentColor, noActionBarTheme, theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTheme(this);
        setContentView(R.layout.activity_login);
        ConstraintLayout clLogin = findViewById(R.id.clLogin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.login));
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.loginLayout);
        TiUser = findViewById(R.id.tiCode);
        TiPass = findViewById(R.id.tiPass);
        TiUser.setErrorEnabled(true);
        TiPass.setErrorEnabled(true);
        EtUser = TiUser.getEditText();
        EtPass = TiPass.getEditText();
        EtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TiUser.setErrorEnabled(false);
                TiUser.setErrorEnabled(true);
            }
        });
        EtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TiPass.setErrorEnabled(false);
                TiPass.setErrorEnabled(true);
            }
        });
        Button btnLogin = findViewById(R.id.btnLogin);
        setColor(clLogin, btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Code = EtUser.getText().toString();
                Pass = EtPass.getText().toString();
                if (!Code.equals("") && !Pass.equals("")) {
                    if (isNetAvailible(LoginActivity.this)) {
                        methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        new AsyncTaskLogin().execute(view);
                    } else
                        Snackbar.make(view, getString(R.string.unavalible_internet), Snackbar.LENGTH_LONG).show();
                } else {
                    if (Code.equals(""))
                        TiUser.setError(getString(R.string.login_blank_user));
                    if (Pass.equals(""))
                        TiPass.setError(getString(R.string.login_blank_pass));
                }
            }
        });
    }

    public void login(final View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Url = getString(R.string.server_url)+"/rest/usuarios.php?u="+md5(Code)+"&p="+md5(Pass);
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response);
                try {
                    JSONArray jsonArray  = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Name = jsonObject.getString("nombre")+" "+
                                jsonObject.getString("appaterno")+" "+
                                jsonObject.getString("apmaterno");
                        Code = jsonObject.getString("codigo");
                        Email = jsonObject.getString("correo");
                        University = jsonObject.getString("universidad");
                        Career = jsonObject.getString("carrera");
                        if (University.toLowerCase().equals("centro universitario de ciencias exactas e ingenierias")) {
                            accentColor = R.color.CUCEI;
                            noActionBarTheme = R.style.CUCEI_NoActionBar;
                            theme = R.style.CUCEI;
                            Acronym = "CUCEI";
                            Image = "2.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de arte, arquitectura y diseño")) {
                            accentColor = R.color.CUAAD;
                            noActionBarTheme = R.style.CUAAD_NoActionBar;
                            theme = R.style.CUAAD;
                            Acronym = "CUAAD";
                            Image = "3.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de los altos")) {
                            accentColor = R.color.CUAltos;
                            noActionBarTheme = R.style.CUAltos_NoActionBar;
                            theme = R.style.CUAltos;
                            Acronym = "CUAltos";
                            Image = "4.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias")) {
                            accentColor = R.color.CUCBA;
                            noActionBarTheme = R.style.CUCBA_NoActionBar;
                            theme = R.style.CUCBA;
                            Acronym = "CUCBA";
                            Image = "5.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de ciencias economico administrativas")) {
                            accentColor = R.color.CUCEA;
                            noActionBarTheme = R.style.CUCEA_NoActionBar;
                            theme = R.style.CUCEA;
                            Acronym = "CUCEA";
                            Image = "6.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de la cienega")) {
                            accentColor = R.color.CUCienega;
                            noActionBarTheme = R.style.CUCienega_NoActionBar;
                            theme = R.style.CUCienega;
                            Acronym = "CUCienega";
                            Image = "7.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de la costa")) {
                            accentColor = R.color.CUCosta;
                            noActionBarTheme = R.style.CUCosta_NoActionBar;
                            theme = R.style.CUCosta;
                            Acronym = "CUCosta";
                            Image = "8.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de ciencias de la salud")) {
                            accentColor = R.color.CUCS;
                            noActionBarTheme = R.style.CUCS_NoActionBar;
                            theme = R.style.CUCS;
                            Acronym = "CUCS";
                            Image = "9.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de ciencias sociales y humanidades")) {
                            accentColor = R.color.CUCSH;
                            noActionBarTheme = R.style.CUCSH_NoActionBar;
                            theme = R.style.CUCSH;
                            Acronym = "CUCSH";
                            Image = "10.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de la costa sur")) {
                            accentColor = R.color.CUCSur;
                            noActionBarTheme = R.style.CUCSur_NoActionBar;
                            theme = R.style.CUCSur;
                            Acronym = "CUCSur";
                            Image = "11.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de los lagos")) {
                            accentColor = R.color.CULagos;
                            noActionBarTheme = R.style.CULagos_NoActionBar;
                            theme = R.style.CULagos;
                            Acronym = "CULagos";
                            Image = "12.jpg";
                        } else if (University.toLowerCase().equals("centro universitario del norte")) {
                            accentColor = R.color.CUNorte;
                            noActionBarTheme = R.style.CUNorte_NoActionBar;
                            theme = R.style.CUNorte;
                            Acronym = "CUNorte";
                            Image = "13.jpg";
                        } else if (University.toLowerCase().equals("centro universitario del sur")) {
                            accentColor = R.color.CUSur;
                            noActionBarTheme = R.style.CUSur_NoActionBar;
                            theme = R.style.CUSur;
                            Acronym = "CUSur";
                            Image = "14.jpg";
                        } else if (University.toLowerCase().equals("centro universitario de tonala")) {
                            accentColor = R.color.CUTonala;
                            noActionBarTheme = R.style.CUTonala_NoActionBar;
                            theme = R.style.CUTonala;
                            Acronym = "CUTonala";
                            Image = "15.png";
                        } else {
                            accentColor = R.color.colorAccent;
                            noActionBarTheme = R.style.AppTheme_NoActionBar;
                            theme = R.style.AppTheme;
                            Acronym = "CUValles";
                            Image = "1.png";
                        }

                        user = new User(Code, Name, Email, University, Career, Acronym, Image, accentColor, noActionBarTheme, theme);
                        saveSession();
                        Intent login = new Intent().setClass(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",user);
                        login.putExtras(bundle);
                        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);
                        finish();
                    } else {
                        Snackbar.make(view, R.string.login_invalid, Snackbar.LENGTH_LONG).show();
                        showLogin();
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }});
        requestQueue.add(request);
    }

    private void saveSession () {
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("code", Code);
        editor.putString("name", Name);
        editor.putString("email", Email);
        editor.putString("university", University);
        editor.putString("career", Career);
        editor.putString("acronym", Acronym);
        editor.putString("image", Image);
        editor.putInt("accent", accentColor);
        editor.putInt("noActionBarTheme", noActionBarTheme);
        editor.putInt("theme", theme);
        editor.apply();
    }

    private void setColor(ConstraintLayout layout, Button button) {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        switch (preferences.getInt("theme", 0)) {
            case 0:
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 1:
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryRed));
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkRed));
                break;
            case 2:
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryPurple));
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkPurple));
                break;
            case 3:
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryGreen));
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkGreen));
                break;
            case 4:
                layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryAndroid));
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkAndroid));
                break;
        }
    }

    public void showLogin() {
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

    public class AsyncTaskLogin extends AsyncTask<View, Integer, Void> {

        @Override
        protected void onPreExecute() {
            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(final View... views) {
            int pause = 0;
            while (pause < 100) {
                pause+=10;
                publishProgress(pause);
                SystemClock.sleep(1);
            }

            String Url = "http://148.202.89.11/api_alumnos/valida.php?id="+Code;
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("respuesta").equalsIgnoreCase("existe"))
                            login(views[0]);
                        else {
                            Snackbar.make(views[0], getString(R.string.invalid_code), Snackbar.LENGTH_LONG).show();
                            showLogin();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Error: ", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                }});
            requestQueue.add(request);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}
