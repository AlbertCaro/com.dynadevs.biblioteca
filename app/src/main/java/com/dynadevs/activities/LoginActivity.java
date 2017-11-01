package com.dynadevs.activities;

import android.content.Context;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    TextInputLayout EtUser, EtPass;
    Button BtnLogin;
    Context context = this;
    User user;

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
                final String User = EtUser.getEditText().getText().toString();
                String Pass = EtPass.getEditText().getText().toString();

                if (!User.equals("") && !Pass.equals("")) {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String Url = "http://albertocaro.000webhostapp.com/biblioteca/rest/usuarios.php?u="+User+"&p="+Pass;
                    StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray  = new JSONArray(response);
                                if (jsonArray.length() != 0) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    user = new User(
                                            jsonObject.getString("Codigo"),
                                            jsonObject.getString("Usuario"),
                                            "Tecnologías de la Información"
                                    );

                                    Toast.makeText(LoginActivity.this,"Bienvenido "+User,Toast.LENGTH_SHORT).show();
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
                                e.printStackTrace();
                            }
                        }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "ERROR: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }});
                    requestQueue.add(request);
                } else {
                    if (User.equals(""))
                        EtUser.setError(getString(R.string.login_blank_user));
                    if (Pass.equals(""))
                        EtPass.setError(getString(R.string.login_blank_pass));
                }
            }
        });
    }
}
