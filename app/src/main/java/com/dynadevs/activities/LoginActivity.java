package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Map;
import java.util.Set;

import static com.dynadevs.classes.Utilities.isNetAvailible;
import static com.dynadevs.classes.Utilities.md5;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout EtUser, EtPass;
    Button BtnLogin;
    Context context = this;
    User user;

    String Code, Name, Email, University, Career, Acronym, Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EtUser = (TextInputLayout) findViewById(R.id.etCode);
        EtPass = (TextInputLayout) findViewById(R.id.etPass);
        BtnLogin = (Button) findViewById(R.id.btnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String User = EtUser.getEditText().getText().toString();
                String Pass = EtPass.getEditText().getText().toString();

                if (!User.equals("") && !Pass.equals("")) {
                    String Url = getString(R.string.server_url)+"biblioteca/rest/usuarios.php?u="+md5(User)+"&p="+md5(Pass);
                    if (isNetAvailible(LoginActivity.this, LoginActivity.this)) {
                        login(view, Url);
                    } else
                        Snackbar.make(view, getString(R.string.unavalible_internet), Snackbar.LENGTH_LONG).show();
                } else {
                    if (User.equals(""))
                        EtUser.setError(getString(R.string.login_blank_user));
                    if (Pass.equals(""))
                        EtPass.setError(getString(R.string.login_blank_pass));
                }
            }
        });
    }

    public void login(final View view, String Url) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                        Toast.makeText(LoginActivity.this, "ERROR: "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "ERROR: "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        editor.commit();
    }
}
