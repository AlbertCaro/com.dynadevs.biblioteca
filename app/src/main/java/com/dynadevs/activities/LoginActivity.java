package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button BtnLogin;
    private User user;
    private ConstraintLayout ClLogin;

    String Code, Name, Email, University, Career, Acronym, Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentTheme(this);
        setContentView(R.layout.activity_login);
        ClLogin = findViewById(R.id.clLogin);
        TiUser = findViewById(R.id.tiCode);
        TiPass = findViewById(R.id.tiPass);
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
            }
        });
        BtnLogin = findViewById(R.id.btnLogin);
        setColor(ClLogin, BtnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String Code = EtUser.getText().toString();
                String Pass = EtPass.getText().toString();

                if (!Code.equals("") && !Pass.equals("")) {
                    String Url = getString(R.string.server_url)+"biblioteca/rest/usuarios.php?u="+md5(Code)+"&p="+md5(Pass);
                    if (isNetAvailible(LoginActivity.this, LoginActivity.this)) {
                        login(view, Url);
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

    public void login(final View view, String Url) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray  = new JSONArray(response);
                        if (jsonArray.length() != 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Name = jsonObject.getString("Nombre")+" "+
                                    jsonObject.getString("ApPaterno")+" "+
                                    jsonObject.getString("ApMaterno");
                            Code = jsonObject.getString("Codigo");
                            Email = jsonObject.getString("Correo");
                            University = jsonObject.getString("Universidad");
                            Career = jsonObject.getString("Carrera");
                            Acronym = jsonObject.getString("Siglas");
                            Image = jsonObject.getString("Imagen");
                            user = new User(Code, Name, Email, University, Career, Acronym, Image);
                            saveSession();
                            Intent login = new Intent().setClass(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user",user);
                            login.putExtras(bundle);
                            startActivity(login);
                            finish();
                        } else {
                            Snackbar.make(view, R.string.login_invalid, Snackbar.LENGTH_LONG).show();
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
}
