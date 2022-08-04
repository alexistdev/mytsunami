package com.malik.mytstunami;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.malik.mytstunami.utils.SessionHandle;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private EditText mEmail,mPassword;
    private Button mLogin;
    private ProgressDialog pDialog;
    private TextView mDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if (SessionHandle.isLoggedIn(this)) {
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Daftar.class);
                startActivity(intent);
                finish();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (cekEmail(email)) {
                        checkLogin(email, password);
                    } else {
                        pesan("Masukkan email yang valid !");
                    }
                } else {
                    pesan("Semua kolom harus diisi!");
                }
            }
        });
    }

    private void checkLogin(final String email, final String password) {
        tampilkanDialog();
        try {
            sembunyikanDialog();
            if(email.equals("user@gmail.com") && password.equals("1234")){
                if (SessionHandle.login(Login.this, "2")) {
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                pesan("Username dan password tidak sama!");
            }
        }catch (Exception e){
            sembunyikanDialog();
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }

    private boolean cekEmail(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public void init() {
        mLogin = findViewById(R.id.btn_login);
        mDaftar = findViewById(R.id.btn_daftar);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }
    private void tampilkanDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void sembunyikanDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void pesan(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}