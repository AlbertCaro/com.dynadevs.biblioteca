package com.dynadevs.activities;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dynadevs.classes.User;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.dynadevs.classes.Utilities.loadSesion;
import static com.dynadevs.classes.Utilities.setCurrentThemeActivity;

public class HelpActivity extends AppCompatActivity {
    private TextInputLayout TiSubject, TiMessage;
    private EditText EtSubject, EtMessage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentThemeActivity(this);
        setContentView(R.layout.activity_help);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_help);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        user = (User) (bundle != null ? bundle.getSerializable("user") : loadSesion(this));
        TiSubject = findViewById(R.id.tiSubject);
        TiMessage = findViewById(R.id.tiMessage);
        EtSubject = TiSubject.getEditText();
        EtMessage = TiMessage.getEditText();
        EtSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TiSubject.setErrorEnabled(false);
            }
        });
        EtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TiMessage.setErrorEnabled(false);
            }
        });
        Button btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subject = user.getName()+": "+EtSubject.getText().toString();
                String Message = "<p>\""+EtMessage.getText().toString()+"\"</p>"
                        +"<strong>"+getString(R.string.user_info)+"</strong>"
                        +"<p>"+user.getName()+"</p>"
                        +"<p>"+user.getCode()+"</p>"
                        +"<p>"+user.getEmail()+"</p>"
                        +"<p>"+user.getUniversity()+"</p>"
                        +"<p>"+user.getCareer()+"</p>";
                if (!EtSubject.getText().toString().equals("") && !EtMessage.getText().toString().equals("")) {
                    sendMessage(Subject, Message);
                } else {
                    if (EtSubject.getText().toString().equals(""))
                        TiSubject.setError(getString(R.string.subject_error));
                    if (EtMessage.getText().toString().equals(""))
                        TiMessage.setError(getString(R.string.message_error));
                }
            }
        });
    }

    public void sendMessage(String Subject, String Content) {
        ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loggin_in), true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.connectiontimeout", "5000");
        properties.put("mail.smtp.timeout", "1000");

        try {
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("dynadevsapps@gmail.com", "fiestaencasa");
                }
            });
            if (session != null) {
                Address[] addresses = new Address[] {
                        new InternetAddress("jonathanperez@alumnos.udg.mx"),
                        new InternetAddress("alberto.cnavarro@alumnos.udg.mx"),
                        new InternetAddress("luisgarcia@alumnos.udg.mx")
                };
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("dynadevsapps@gmail.com"));
                message.setSubject(Subject);
                message.setRecipients(Message.RecipientType.TO, addresses);
                message.setContent(Content, "text/html; charset=utf-8");
                Transport.send(message);
                Toast.makeText(this, getString(R.string.message_send), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (MessagingException e) {
            Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show();
        }
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
