package com.albertocaro.biblioteca;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout EtUser, EtPass;
    Button BtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EtUser = (TextInputLayout) findViewById(R.id.etCode);
        EtPass = (TextInputLayout) findViewById(R.id.etPass);
        BtnLogin = (Button) findViewById(R.id.btnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String User = EtUser.getEditText().getText().toString();
                String Pass = EtPass.getEditText().getText().toString();

                if (!User.equals("") && !Pass.equals("")) {
                    if (User.equals("215818158") && Pass.equals("hola123")) {
                        Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent login = new Intent().setClass(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                        finish();
                    } else
                        Snackbar.make(view, R.string.login_invalid, Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
